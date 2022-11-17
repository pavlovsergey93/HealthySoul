package com.gmail.pavlovsv93.healthysoul.ui.tests.questions.questionsadapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentTestQuestionBinding

class TestQuestionFragment : Fragment() {
	companion object {
		const val ARG_ID_QUESTION = "TestQuestionFragment.ARG_ID_QUESTION"
//		fun newInstance(questionId: String): TestQuestionFragment {
//			return TestQuestionFragment().apply {
//				arguments = Bundle().apply {
//					putString(ARG_ID_QUESTION, questionId)
//				}
//			}
//		}
	}

	private var _binding: FragmentTestQuestionBinding? = null
	private val binding get() = _binding!!
	private val adapter: AnswerAdapter = AnswerAdapter(object : OnClickOnAnswer{
		override fun showNextQuestion(id: String) {
			//todo запрос на получение вопроса
		}

		override fun showHint(id: String) {
			// todo запрос на получение совета
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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val id = arguments?.getString(ARG_ID_QUESTION)
		id?.let {
			//todo Обработка запроса на получение данных вопроса
			binding.tvQuestion.text = id
		}
		val recyclerView = binding.rvAnswers
		recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
	}

	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}
}