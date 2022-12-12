package com.gmail.pavlovsv93.healthysoul.ui.note_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.NoteDetails
import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.Notebook
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentNoteBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private val args: NoteFragmentArgs by navArgs()

    private val viewModel by viewModel<NoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCall)
        initClickListeners()
        initArgs()
        initObserver()
    }

    private fun initClickListeners() {
        binding.saveButton.setOnClickListener {
            if (args.noteId == ADD_NEW_NOTE) saveNote() else updateNote()
        }
        binding.deleteButton.setOnClickListener {
            deleteNote()
        }
    }

    private fun initArgs() {
        when (args.noteId) {
            ADD_NEW_NOTE -> setVisibilityLoading()
            else -> viewModel.getNote(args.noteId)
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.state.collect {
                renderState(it)
            }
        }
    }

    private fun renderState(state: NoteDetails) {
        if (!state.isLoadingDone) return

        if (state.updateNote || state.saveNote || state.deleteNote) {
            findNavController().popBackStack()
        }
        
        with(binding) {
            setVisibilityLoading()
            titleHead.setText(state.note.title)
            textBody.setText(state.note.content)
        }
    }

    private fun setVisibilityLoading() {
        Log.e("pie", "setVisibilityLoading: ", )
        with(binding) {
            groupLoading.visibility = View.VISIBLE
            groupNotLoading.visibility = View.GONE
        }
    }

    private fun setNotVisibilityLoading() {
        Log.e("pie", "setNotVisibilityLoading: ", )
        with(binding) {
            groupLoading.visibility = View.GONE
            groupNotLoading.visibility = View.VISIBLE
        }
    }

    private fun saveNote() {
        setNotVisibilityLoading()
        viewModel.saveNote(
            Notebook(
                id = UUID.randomUUID().hashCode(),
                title = binding.titleHead.text.toString(),
                content = binding.textBody.text.toString()
            )
        )
    }

    private fun updateNote() {
        setNotVisibilityLoading()
        viewModel.updateNote(
            Notebook(
                id = args.noteId,
                title = binding.titleHead.text.toString(),
                content = binding.textBody.text.toString()
            )
        )
    }

    private fun deleteNote() {
        viewModel.deleteNote(
            Notebook(args.noteId)
        )
    }

    private val backCall = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ADD_NEW_NOTE = -1
    }
}