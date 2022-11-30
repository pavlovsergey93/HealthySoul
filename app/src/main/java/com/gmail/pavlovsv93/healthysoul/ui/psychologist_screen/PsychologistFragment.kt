package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.data.data.RemoteDataSource
import com.gmail.data.entity.DataSourceInterface
import com.gmail.data.entity.PsychologistEntity
import com.gmail.data.repository.FirebaseRepository
import com.gmail.data.repository.RepositoryInterface
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentPsychologistBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class PsychologistFragment : Fragment() {

    private var _binding: FragmentPsychologistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PsychologistViewModel by lazy {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val repository: RepositoryInterface = FirebaseRepository(db)
        val dataSource: DataSourceInterface = RemoteDataSource(repository)
        PsychologistViewModel(dataSource = dataSource)
    }

    private val adapter: PsychologistFragmentAdapter = PsychologistFragmentAdapter()

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
                val exception = state.exception
            }
            is AppState.OnLoading -> {
                val loaded = state.load
            }
            is AppState.OnShowMessage -> {
                val message = state.message
            }
            is AppState.OnSuccess<*> -> {
                val category: List<PsychologistEntity> = state.success as List<PsychologistEntity>
                adapter.updatePsychologistList(category)
            }
        }
    }

    private fun initRecyclerView() {
        //ToDo onClick

        binding.fragmentPsychologistRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.fragmentPsychologistRecyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}