package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.psychologistlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class PsychologistFragment : Fragment() {

    private var _binding: FragmentPsychologistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PsychologistViewModel by viewModel(named(PSYCHOLOGIST_VIEW_MODEL))

    private val adapter: PsychologistFragmentAdapter = PsychologistFragmentAdapter { idPsychologist ->
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
        initRecyclerView()
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getData().collect { state ->
                    ranger(state)
                }
            }
        }
        viewModel.getAllPsychologist()
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
                        viewModel.getAllPsychologist()
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