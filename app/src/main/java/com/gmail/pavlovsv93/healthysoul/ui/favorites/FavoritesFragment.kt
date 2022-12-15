package com.gmail.pavlovsv93.healthysoul.ui.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.gmail.data.entity.PsychologistEntity
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentFavoritesBinding
import com.gmail.pavlovsv93.healthysoul.di.VIEW_MODEL_FAVORITES
import com.gmail.pavlovsv93.healthysoul.ui.favorites.adapter.ClickedOnFavorites
import com.gmail.pavlovsv93.healthysoul.ui.favorites.adapter.FavoritesFragmentAdapter
import com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.details.PsychologistDetailsFragment
import com.gmail.pavlovsv93.healthysoul.utils.AppState
import com.gmail.pavlovsv93.healthysoul.utils.showMessage
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class FavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModel(named(VIEW_MODEL_FAVORITES))
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getData().collect { state ->
                    ranger(state)
                }
            }
        }
        viewModel.getAllFavorites()
    }

    private val adapter: FavoritesFragmentAdapter =
        FavoritesFragmentAdapter(object : ClickedOnFavorites {
            override fun onClick(id: String) {
                val data = Bundle().apply {
                    putString(PsychologistDetailsFragment.ARG_ID_PSYCHOLOGIST, id)
                }
                findNavController().navigate(R.id.psychologistDetailsFragment, data)
            }

            override fun deleteFavorite(psychologist: PsychologistEntity) {
                TODO("Not yet implemented")
            }

            override fun addFavorite(psychologist: PsychologistEntity) {
                TODO("Not yet implemented")
            }
        })

    private fun ranger(state: AppState) {
        when (state) {
            is AppState.OnException -> {
                binding.progressPsychologist.visibility = View.GONE
                val message = state.exception.message.toString()
                binding.root.showMessage(
                    str = message,
                    actionText = getString(R.string.reload),
                    action = {
                        viewModel.getAllFavorites()
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


//        fun showSnackBar(message: String) {
//            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).setAction("Okay") {
//            }.show()
//        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
//            adapter.clear()
    }
}
