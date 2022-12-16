package com.gmail.pavlovsv93.healthysoul.ui.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.data.data.room.RoomEntity
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentFavoritesBinding
import com.gmail.pavlovsv93.healthysoul.di.VIEW_MODEL_FAVORITES
import com.gmail.pavlovsv93.healthysoul.ui.favorites.adapter.ClickedOnFavorites
import com.gmail.pavlovsv93.healthysoul.ui.favorites.adapter.FavoritesAdapter
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
        initRecyclerView()
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getData().collect { state ->
                    ranger(state)
                }
            }
        }
        viewModel.getAllFavorites()
    }

    private fun initRecyclerView() {
        val rv = binding.recyclerview
        rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv.adapter = adapter
    }

    private val adapter: FavoritesAdapter = FavoritesAdapter(object : ClickedOnFavorites{
        override fun onClick(id: String) {
            TODO("Not yet implemented")
        }

        override fun deleteFavorite(psychologist: RoomEntity) {
            viewModel.deleteFavorite()
        }
    })

    private fun ranger(state: AppState) {
        when (state) {
            is AppState.OnException -> {
                binding.progress.visibility = View.GONE
                val message = state.exception.message.toString()
                binding.root.showMessage(
                    str = message,
                    actionText = getString(R.string.reload),
                    action = {
                        viewModel.getAllFavorites()
                    })
            }
            is AppState.OnLoading -> {
                binding.progress.visibility = View.VISIBLE
            }
            is AppState.OnShowMessage -> {
                binding.progress.visibility = View.GONE
                val message = state.message
                binding.root.showMessage(message)
            }
            is AppState.OnSuccess<*> -> {
                binding.progress.visibility = View.GONE
                val listFavorite: List<RoomEntity> = state.success as List<RoomEntity>
                adapter.submitListDiseases(listFavorite)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

