package com.gmail.pavlovsv93.healthysoul.ui.psychologists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentPsychologistsBinding

class PsychologistsFragment: Fragment() {

	private var _binding: FragmentPsychologistsBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentPsychologistsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}
}