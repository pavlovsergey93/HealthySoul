package com.gmail.pavlovsv93.healthysoul.ui.tests.testscategory

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
import com.gmail.data.repository.testscategory.TestsCategoryDataSource
import com.gmail.data.repository.testscategory.TestsCategoryDataSourceInterface
import com.gmail.data.repository.testscategory.TestsCategoryRepository
import com.gmail.data.repository.testscategory.TestsCategoryRepositoryInterface
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentTestsBinding
import com.gmail.pavlovsv93.healthysoul.ui.tests.AppState
import com.gmail.pavlovsv93.healthysoul.ui.tests.TestQuestionFragment
import com.gmail.pavlovsv93.healthysoul.ui.tests.testscategory.testsadapter.TestsAdapter
import com.gmail.pavlovsv93.healthysoul.utils.GeneralTestData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class TestsFragment : Fragment() {
	private var _binding: FragmentTestsBinding? = null
	private val binding get() = _binding!!

	private val viewModel: TestsViewModel by lazy {
		val db: FirebaseFirestore = FirebaseFirestore.getInstance()
		val repository: TestsCategoryRepositoryInterface = TestsCategoryRepository(db)
		val dataSource: TestsCategoryDataSourceInterface = TestsCategoryDataSource(repository)
		TestsViewModel(dataSource = dataSource)
	}
	private val adapter: TestsAdapter = TestsAdapter { data ->
		parentFragmentManager.beginTransaction()
			.replace(R.id.nav_host_fragment, TestQuestionFragment.newInstance(data))
			.addToBackStack(null)
			.commit()
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
		lifecycleScope.launch {
			lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.getData().collect{ state ->
					ranger(state)
				}
			}
		}
		viewModel.getTestsCategory()
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
				val category: List<GeneralTestData> = state.success as List<GeneralTestData>
				adapter.setData(category)
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