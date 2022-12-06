package com.gmail.pavlovsv93.healthysoul.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
    }

    private fun observeAuthenticationState() {
        val name = String.format(FirebaseAuth.getInstance().currentUser?.displayName?:"Дарья")
        val number = String.format(FirebaseAuth.getInstance().currentUser?.phoneNumber?:"2-51-97")
        val email = String.format(FirebaseAuth.getInstance().currentUser?.email?:"kangert")
        binding.nameText.setText(name)
        binding.phoneNumber.setText(number)
        binding.emailText.setText(email)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}