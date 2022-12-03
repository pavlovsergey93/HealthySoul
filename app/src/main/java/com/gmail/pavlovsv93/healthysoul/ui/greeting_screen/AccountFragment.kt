package com.gmail.pavlovsv93.healthysoul.ui.greeting_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
                /* ...formatArgs = */ FirebaseAuth.getInstance().currentUser?.displayName))

        binding.tvWelcome.text = hello

        binding.nextButtonText.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext())
            findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToTabsFragment())
        }

        view?.findViewById<Button>(R.id.bntSignOut)?.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext())
        }
        view?.findViewById<Button>(R.id.btnDeleteAccount)?.setOnClickListener {
            AuthUI.getInstance().delete(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}