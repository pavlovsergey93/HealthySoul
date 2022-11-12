package com.gmail.pavlovsv93.healthysoul.ui.greeting_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentGreetingBinding
import kotlinx.coroutines.launch

class GreetingFragment : Fragment() {

    private var _binding: FragmentGreetingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClick()
    }

    private fun initClick() {
        binding.nextBtn.setOnClickListener {
            lifecycleScope.launch {
                findNavController().navigate(GreetingFragmentDirections.actionGreetingFragmentToTabsFragment())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}