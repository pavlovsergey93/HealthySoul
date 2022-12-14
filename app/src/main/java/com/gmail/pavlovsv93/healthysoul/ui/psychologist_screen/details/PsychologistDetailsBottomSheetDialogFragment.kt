package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentPsychologistDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PsychologistDetailsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPsychologistDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val args: PsychologistDetailsBottomSheetDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPsychologistDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayContacts()
        setIntent()
    }

    private fun displayContacts() {
        binding.TextPhone.text = args.phone
        binding.TextEmailAddress.text = args.email
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (context?.let { intent.resolveActivity(it.packageManager) } != null) {
            startActivity(intent)
        }
    }

    private fun composeEmail(address: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$address")
            putExtra(Intent.EXTRA_EMAIL, data)
        }
        if (context?.let { intent.resolveActivity(it.packageManager) } != null) {
            startActivity(intent)
        }
    }

    private fun setIntent() {
        val onClickPhoneButton = binding.TextPhone
        onClickPhoneButton.setOnClickListener {
            val contact = args.phone
            if (contact != null) {
                dialPhoneNumber(contact)
            }
        }
        val onClickEmailButton = binding.TextEmailAddress
        onClickEmailButton.setOnClickListener {
            val contact = args.email
            if (contact != null) {
                composeEmail(contact)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}