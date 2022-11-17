package com.gmail.pavlovsv93.healthysoul.ui.tests.questions

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
import com.gmail.data.entity.tests.questionentity.QuestionEntity
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentTestQuestionBinding
import com.gmail.pavlovsv93.healthysoul.di.QUESTION_VIEW_MODEL
import com.gmail.pavlovsv93.healthysoul.ui.tests.AppState
import com.gmail.pavlovsv93.healthysoul.ui.tests.questions.questionsadapter.AnswerAdapter
import com.gmail.pavlovsv93.healthysoul.ui.tests.questions.questionsadapter.OnClickOnAnswer
import com.gmail.pavlovsv93.healthysoul.utils.showMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class TestQuestionFragment : Fragment() {
	companion object {
		const val ARG_ID_QUESTION = "TestQuestionFragment.ARG_ID_QUESTION"
	}

	private val viewModel: QuestionsViewModel by viewModel(named(QUESTION_VIEW_MODEL))
	private var _binding: FragmentTestQuestionBinding? = null
	private val binding get() = _binding!!
	private val adapter: AnswerAdapter = AnswerAdapter(object : OnClickOnAnswer {
		override fun showNextQuestion(id: String) {
			viewModel.getQuestion(id)
		}

		override fun showBackQuestion(id: String) {
			viewModel.getQuestion(id)
		}

		override fun showHint(id: String) {
			viewModel.getHint(id)
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
	}

	private fun ranger(state: AppState) {
		when (state) {
			AppState.OnEmpty -> {
				binding.lpiProgress.visibility = View.GONE
			}
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
				val question: QuestionEntity = state.success as QuestionEntity
				displayQuestion(question)
			}
		}
	}

	private fun displayQuestion(question: QuestionEntity) {
		with(binding){
			tvQuestion.text = question.question
			adapter.setData(question.answers)
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