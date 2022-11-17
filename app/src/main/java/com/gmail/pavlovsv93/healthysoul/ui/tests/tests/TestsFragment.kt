package com.gmail.pavlovsv93.healthysoul.ui.tests.tests

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.data.repository.testscategory.TestsCategoryDataSource
import com.gmail.data.repository.testscategory.TestsCategoryDataSourceInterface
import com.gmail.data.repository.testscategory.TestsCategoryRepository
import com.gmail.data.repository.testscategory.TestsCategoryRepositoryInterface
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentTestsBinding
import com.gmail.pavlovsv93.healthysoul.di.TESTS_VIEW_MODEL
import com.gmail.pavlovsv93.healthysoul.ui.tests.AppState
import com.gmail.pavlovsv93.healthysoul.ui.tests.questions.questionsadapter.TestQuestionFragment
import com.gmail.pavlovsv93.healthysoul.ui.tests.tests.testsadapter.TestsAdapter
import com.gmail.pavlovsv93.healthysoul.utils.GeneralTestData
import com.gmail.pavlovsv93.healthysoul.utils.showMessage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class TestsFragment : Fragment() {
	private var _binding: FragmentTestsBinding? = null
	private val binding get() = _binding!!

	private val viewModel: TestsViewModel by viewModel(named(TESTS_VIEW_MODEL))
	private val adapter: TestsAdapter = TestsAdapter { idQuestion ->
		val data = Bundle().apply {
			putString(TestQuestionFragment.ARG_ID_QUESTION, idQuestion)
		}
		findNavController().navigate(R.id.testQuestionFragment, data)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentTestsBinding.inflate(inflater, container, false)
		return binding.root
	}

	@SuppressLint("UnsafeRepeatOnLifecycleDetector")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		applyRecyclerView()
		lifecycleScope.launch(Dispatchers.IO) {
			lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.getData()
					.collect{ state ->
						withContext(Dispatchers.Main){
							ranger(state)
						}
				}
			}
		}
		viewModel.getTestsCategory()
	}

	private fun ranger(state: AppState) {
		when (state) {
			is AppState.OnException -> {
				binding.lpiProgress.visibility = View.GONE
				val message = state.exception.message.toString()
				binding.root.showMessage(message)
			}
			is AppState.OnLoading -> {
				binding.lpiProgress.visibility = View.VISIBLE
			}
			is AppState.OnShowMessage -> {
				binding.lpiProgress.visibility = View.GONE
				val message = state.message
				binding.root.showMessage(message)
			}
			is AppState.OnSuccess<*> -> {
				binding.lpiProgress.visibility = View.GONE
				val category: List<GeneralTestData> = state.success as List<GeneralTestData>
				adapter.setData(category)
			}
			AppState.OnEmpty -> {
				binding.lpiProgress.visibility = View.GONE
			}
		}
	}

	private fun applyRecyclerView() {
		val recyclerView = binding.rvListTests
		recyclerView.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		recyclerView.adapter = adapter
	}

	override fun onDestroyView() {
		_binding = null
		super.onDestroyView()
	}
}