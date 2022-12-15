package com.gmail.pavlovsv93.healthysoul.ui.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gmail.data.data.room.RoomEntity
import com.gmail.data.entity.PsychologistEntity
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentFavoritesBinding
import com.gmail.pavlovsv93.healthysoul.di.VIEW_MODEL_FAVORITES
import com.gmail.pavlovsv93.healthysoul.utils.AppState
import com.gmail.pavlovsv93.healthysoul.utils.showMessage
import com.google.android.material.snackbar.Snackbar
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

    private fun ranger(state: AppState) {
        when (state) {
            is AppState.OnException -> {
                val message = state.exception.message.toString()
                binding.root.showMessage(
                    str = message,
                    actionText = getString(R.string.reload),
                    action = {
                        viewModel.getAllFavorites()
                    })
            }
            is AppState.OnLoading -> {

            }
            is AppState.OnShowMessage -> {
                val message = state.message
                binding.root.showMessage(message)
            }
            is AppState.OnSuccess<*> -> {
                val category: List<RoomEntity> = state.success as List<RoomEntity>
                adapter.updatePsychologistList(category)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu, menu)
    }

    fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).setAction("Okay") {
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
//        favoriteRecipesAdapter.clearContext()
    }


}