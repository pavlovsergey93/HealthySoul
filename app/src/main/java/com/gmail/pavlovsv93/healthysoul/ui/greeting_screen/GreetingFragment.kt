package com.gmail.pavlovsv93.healthysoul.ui.greeting_screen

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentGreetingBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
const val ARG_NAME_USER = "ARG_NAME_USER"
class GreetingFragment : Fragment() {

    private var _binding: FragmentGreetingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val name = currentUser.displayName ?: "Неизвестный"
            updateUI(name)
        }
    }

    private fun updateUI(name: String) {
        // todo доработать необходимый функционал
        val dataName = Bundle().apply {
            putString(ARG_NAME_USER, name)
        }
        findNavController().navigate(GreetingFragmentDirections.actionGreetingFragmentToTabsFragment())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClick()
        binding.loginButton.setOnClickListener {
            signInLauncher.launch(signInIntent())
        }
    }

    private fun initProvider() = listOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.PhoneBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    private fun signInIntent() = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(initProvider())
        .build()

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) { result ->
            this.onSignInResult(result)
        }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == Activity.RESULT_OK) {
            val userName = FirebaseAuth.getInstance().currentUser?.displayName ?: "Неизвестный!"
            updateUI(userName)
        } else {
            Toast.makeText(
                requireContext(), response?.error?.message.toString(), Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initClick() {
        binding.nextBtn.setOnClickListener {
            lifecycleScope.launch {
                findNavController().navigate(GreetingFragmentDirections.actionGreetingFragmentToTabsFragment())
            }
        }
    }
}
