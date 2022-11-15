package com.gmail.pavlovsv93.healthysoul.ui.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentTestsBinding

class TestsFragment : Fragment() {

	private var _binding: FragmentTestsBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentTestsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}
}