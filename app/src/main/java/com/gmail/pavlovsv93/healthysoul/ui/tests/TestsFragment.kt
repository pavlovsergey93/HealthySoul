package com.gmail.pavlovsv93.healthysoul.ui.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentTestsBinding
import com.gmail.pavlovsv93.healthysoul.ui.tests.adapter.TestsAdapter
import my.categoryListTests

class TestsFragment : Fragment() {
	private var _binding: FragmentTestsBinding? = null
	private val binding get() = _binding!!

	private val adapter: TestsAdapter = TestsAdapter { data ->
		// todo обработка нажатий по элементам списка
		Toast.makeText(requireContext(), data.type.toString(), Toast.LENGTH_SHORT).show()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentTestsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val recyclerView = binding.rvListTests
		recyclerView.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		recyclerView.adapter = adapter
		adapter.setData(categoryListTests) // todo загрузить полученый список категорий и подкатегорий
	}

	override fun onDestroyView() {
		_binding = null
		super.onDestroyView()
	}
}