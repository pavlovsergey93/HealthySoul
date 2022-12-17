package com.gmail.pavlovsv93.healthysoul.ui.notebook_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.Notebook
import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.NotebookDetails
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentNotebookBinding
import com.gmail.pavlovsv93.healthysoul.ui.note_screen.NoteFragment.Companion.ADD_NEW_NOTE
import com.gmail.pavlovsv93.healthysoul.utils.DividerItemDecorator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotebookFragment : Fragment() {

    private var _binding: FragmentNotebookBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NotebookAdapter

    private val viewModel by viewModel<NotebookViewModel>()

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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCall)
        viewModel.initialization()
        initListener()
        initRecyclerView()
        initObserver()
    }

    private fun initListener() {
        binding.addNote.setOnClickListener {
            navigateTo(ADD_NEW_NOTE)
        }
    }

    private fun initRecyclerView() {
        adapter = NotebookAdapter(object : NotebookAdapter.Listener {
            override fun onChooseNote(item: Notebook) {
                navigateTo(item.id)
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
        if (!state.isLoadingDone) return
        setNotVisibilityLoading()
        adapter.submitList(state.listNotes)
        if (state.listNotes.isEmpty()) {
            binding.noNotes.visibility = View.VISIBLE
        }
    }

    private fun navigateTo(id: Int) {
        setVisibilityLoading()
        viewModel.closeFragment()
        findNavController().navigate(
            NotebookFragmentDirections.actionNotebookFragmentToNoteFragment(
                id
            )
        )
    }

    private fun setVisibilityLoading() {
        with(binding) {
            groupLoading.visibility = View.VISIBLE
            notebookRecyclerView.visibility = View.GONE
            addNote.visibility = View.GONE
        }
    }

    private fun setNotVisibilityLoading() {
        with(binding) {
            groupLoading.visibility = View.GONE
            notebookRecyclerView.visibility = View.VISIBLE
            addNote.visibility = View.VISIBLE
        }
    }

    private val backCall = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if (backPressTime + DEFAULT_PAUSE > System.currentTimeMillis()) {
                toast.cancel()
                requireActivity().finish()
            } else {
                toast.show()
            }
            backPressTime = System.currentTimeMillis()
        }
    }
    private var backPressTime: Long = 0L
    private val toast: Toast by lazy {
        Toast.makeText(requireContext(), getString(R.string.message_exit), Toast.LENGTH_SHORT)
    }

    companion object{
        const val DEFAULT_PAUSE = 2500L
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}