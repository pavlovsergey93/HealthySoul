package com.gmail.pavlovsv93.healthysoul.ui.greeting_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding: FragmentAccountBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
    }

    private fun observeAuthenticationState() {
        val hello = String.format(
            resources.getString(
                /* id = */ R.string.welcome_message,
                /* ...formatArgs = */ FirebaseAuth.getInstance().currentUser?.displayName?:"пользователь"))

        binding.tvWelcome.text = hello

        binding.nextButtonText.setOnClickListener {
            findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToTabsFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}