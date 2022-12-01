package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentPsychologistBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PsychologistFragment : Fragment(R.layout.fragment_psychologist) {

    private var _binding: FragmentPsychologistBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainFragmentAdapter()

    private val viewModel by viewModel<PsychologistViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.requestAllPsychologist()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPsychologistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.loading.collect {
                binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.error.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.data.collect {

            }
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        //ToDo onClick

        binding.fragmentPsychologistRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        binding.fragmentPsychologistRecyclerView.adapter = adapter
        //adapter.updatePsychologistList()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}