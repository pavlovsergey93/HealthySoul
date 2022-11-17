package com.gmail.pavlovsv93.healthysoul.ui.notebook_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.Notebook
import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.NotebookDetails
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.adapters.NotebookAdapter
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentNotebookBinding
import com.gmail.pavlovsv93.healthysoul.utils.DividerItemDecorator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotebookFragment : Fragment() {

    private var _binding: FragmentNotebookBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NotebookAdapter

    private val viewModel by viewModel<NotebookViewModel>()
    private val database = Firebase.firestore.collection("notebook")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotebookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialization()
        initRecyclerView()
        initObserver()
    }

    private fun initRecyclerView() {
        adapter = NotebookAdapter(object : NotebookAdapter.Listener {
            override fun onChooseNote(item: Notebook) {
                Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
            }
        })
        binding.notebookRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notebookRecyclerView.adapter = adapter
        binding.notebookRecyclerView.addItemDecoration(
            DividerItemDecorator(
                AppCompatResources.getDrawable(
                    requireContext(), R.drawable.divider
                )
            )
        )
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                renderState(state)
            }
        }
    }

    private fun renderState(state: NotebookDetails) {
        if (state.isLoadingDone) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}