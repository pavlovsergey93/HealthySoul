package com.gmail.pavlovsv93.healthysoul.ui.tests.questions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmai.pavlovsv93.healtysoul.domain.models.tests.QuestionEntity
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentTestQuestionBinding
import com.gmail.pavlovsv93.healthysoul.di.QUESTION_VIEW_MODEL
import com.gmail.pavlovsv93.healthysoul.utils.AppState
import com.gmail.pavlovsv93.healthysoul.ui.tests.hint.TestsHintFragment.Companion.ARG_HINT_ID
import com.gmail.pavlovsv93.healthysoul.ui.tests.questions.questionsadapter.AnswerAdapter
import com.gmail.pavlovsv93.healthysoul.ui.tests.questions.questionsadapter.OnClickOnAnswer
import com.gmail.pavlovsv93.healthysoul.utils.showMessage
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class TestQuestionFragment : Fragment() {
    companion object {
        const val ARG_ID_QUESTION = "TestQuestionFragment.ARG_ID_QUESTION"
    }

    private var currentQuestion: QuestionEntity? = null
    private val viewModel: QuestionsViewModel by viewModel(named(QUESTION_VIEW_MODEL))
    private var _binding: FragmentTestQuestionBinding? = null
    private val binding get() = _binding!!
    private val adapter: AnswerAdapter = AnswerAdapter(object : OnClickOnAnswer {
        override fun showNextQuestion(id: String) {
            viewModel.getQuestion(id)
        }

        override fun showHint(id: String) {
            val data = Bundle().apply {
                putString(ARG_HINT_ID, id)
            }
            findNavController().navigate(R.id.testsHintFragment, data)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCall)
        applyRecyclerView()
        val id = arguments?.getString(ARG_ID_QUESTION)
        id?.let { viewModel.getQuestion(it) }
        lifecycleScope.launch(Dispatchers.IO) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getData()
                    .collect { state ->
                        withContext(Dispatchers.Main) {
                            ranger(state)
                        }
                    }
            }
        }
        initFab()
    }

    private fun ranger(state: AppState) {
        when (state) {
            is AppState.OnException -> {
                binding.lpiProgress.visibility = View.GONE
                binding.root.showMessage(state.exception.message.toString())
            }
            AppState.OnLoading -> {
                binding.lpiProgress.visibility = View.VISIBLE
            }
            is AppState.OnShowMessage -> {
                binding.lpiProgress.visibility = View.GONE
                binding.root.showMessage(state.message)
            }
            is AppState.OnSuccess<*> -> {
                binding.lpiProgress.visibility = View.GONE
                currentQuestion = state.success as QuestionEntity
                currentQuestion?.let { displayQuestion(it) }
            }
        }
    }

    private fun displayQuestion(question: QuestionEntity) {
        with(binding) {
            fabBack.visibility = if (question.answers?.first()?.backQuestionId.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            tvQuestion.text = question.question
            question.answers?.let { adapter.setData(it) }
        }
    }

    private fun initFab() {
        binding.fabBack.setOnClickListener {
            currentQuestion?.answers?.first()?.backQuestionId?.let {
                viewModel.getQuestion(it)
            }
        }
        binding.fabHome.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.close_title)
                .setMessage(R.string.close_message)
                .setNegativeButton(getString(R.string.dialog_negative)) { dialog, _ ->
                    dialog.dismiss()
                }.setPositiveButton(getString(R.string.dialog_positive)) { dialog, _ ->
                    findNavController().popBackStack()
                    dialog.dismiss()
                }.show()
        }
    }

    private val backCall = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun applyRecyclerView() {
        val recyclerView = binding.rvAnswers
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}