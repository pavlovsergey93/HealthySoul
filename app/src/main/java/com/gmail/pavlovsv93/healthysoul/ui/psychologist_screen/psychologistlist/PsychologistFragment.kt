package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.psychologistlist

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.data.entity.PsychologistEntity
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentPsychologistBinding
import com.gmail.pavlovsv93.healthysoul.di.PSYCHOLOGIST_VIEW_MODEL
import com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.details.PsychologistDetailsFragment
import com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.psychologistlist.adapter.PsychologistFragmentAdapter
import com.gmail.pavlovsv93.healthysoul.utils.AppState
import com.gmail.pavlovsv93.healthysoul.utils.showMessage
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.io.IOException

class PsychologistFragment : Fragment() {

    private var _binding: FragmentPsychologistBinding? = null
    private val binding get() = _binding!!
    private lateinit var userLocation: GeoPoint

    companion object {
        const val REFRESH_PERIOD = 60000L
        const val MINIMAL_DISTANCE = 100f
        const val MAX_RESULT = 1
    }

    private val viewModel: PsychologistViewModel by viewModel(named(PSYCHOLOGIST_VIEW_MODEL))
    private val adapter: PsychologistFragmentAdapter =
        PsychologistFragmentAdapter { idPsychologist ->
            val data = Bundle().apply {
                putString(PsychologistDetailsFragment.ARG_ID_PSYCHOLOGIST, idPsychologist)
            }
            findNavController().navigate(R.id.psychologistDetailsFragment, data)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPsychologistBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissionGeo()
        initRecyclerView()
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getData().collect { state ->
                    ranger(state)
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getUserLocation()
            } else {
                showDialog()
            }
        }

    private fun getUserLocation() {
        activity?.let { context ->
            when {
                (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED) -> {
                    val managerLocation =
                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    if (managerLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        managerLocation.getProvider(LocationManager.GPS_PROVIDER)?.let {
                            managerLocation.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                REFRESH_PERIOD,
                                MINIMAL_DISTANCE,
                                onLocationListener
                            )
                        }
                    } else {
                        val location =
                            managerLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        if (location == null) {
                            binding.root.showMessage(getString(R.string.error_gps))
                        } else {
                            binding.root.showMessage(getString(R.string.error_gps_local))
                            // todo получение последних данных с устройства
                            userLocation = GeoPoint(location.latitude, location.longitude)
                            viewModel.getAllPsychologist(userLocation)
                        }
                    }
                }
                else -> {

                }
            }
        }
    }

    private val onLocationListener = LocationListener { p0 ->
        userLocation = GeoPoint(p0.latitude, p0.longitude)
        viewModel.getAllPsychologist(userLocation)
    }

    private fun showDialog() {
        activity?.let {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.permission_title))
                .setMessage(getString(R.string.permission_message))
                .setPositiveButton(getString(R.string.dialog_positive)) { dialog, _ ->
                    requestPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    dialog.dismiss()
                }.setNegativeButton(getString(R.string.dialog_negative)) { dialog, _ ->
                    dialog.dismiss()
                }.create()
                .show()
        }
    }

    private fun checkPermissionGeo() {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    getUserLocation()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showDialog()
                }
                else -> {
                    requestPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }
            }
        }
    }

    private fun ranger(state: AppState) {
        when (state) {
            is AppState.OnException -> {
                binding.progressPsychologist.visibility = View.GONE
                val message = state.exception.message.toString()
                binding.root.showMessage(
                    str = message,
                    actionText = getString(R.string.reload),
                    action = {
                        userLocation?.let {
                            viewModel.getAllPsychologist(userLocation)
                        }
                    })
            }
            is AppState.OnLoading -> {
                binding.progressPsychologist.visibility = View.VISIBLE
            }
            is AppState.OnShowMessage -> {
                binding.progressPsychologist.visibility = View.GONE
                val message = state.message
                binding.root.showMessage(message)
            }
            is AppState.OnSuccess<*> -> {
                binding.progressPsychologist.visibility = View.GONE
                val category: List<PsychologistEntity> = state.success as List<PsychologistEntity>
                adapter.updatePsychologistList(category)
            }
        }
    }

    private fun initRecyclerView() {
        binding.fragmentPsychologistRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.fragmentPsychologistRecyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}