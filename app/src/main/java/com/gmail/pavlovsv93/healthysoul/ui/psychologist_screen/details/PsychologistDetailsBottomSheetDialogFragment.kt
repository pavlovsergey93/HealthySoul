package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.gmail.data.entity.PsychologistEntity
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentPsychologistDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PsychologistDetailsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPsychologistDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!
    //val psychologists = mutableListOf<PsychologistEntity>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPsychologistDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayContacts()
        setIntent()
    }

    private fun displayContacts() {
        binding.TextPhone.text = "+7123456789"

//        psychologist.contacts.forEach { contact ->
//            binding.TextPhone.text = contact.titleContact
//        }
        binding.TextEmailAddress.text = "mail@gmail.com"
    }


    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (context?.let { intent.resolveActivity(it.packageManager) } != null) {
            startActivity(intent)
        }
    }

    private fun composeEmail(address: String, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, address)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        if (context?.let { intent.resolveActivity(it.packageManager) } != null) {
            startActivity(intent)
        }
    }

    private fun setIntent() {
        val onClickPhoneButton = binding.TextPhone as TextView
        onClickPhoneButton.setOnClickListener {
            dialPhoneNumber("+7123456789")
            //Toast.makeText(context, "Phone", Toast.LENGTH_SHORT).show()
        }
        val onClickEmailButton = binding.TextEmailAddress as TextView
        onClickEmailButton.setOnClickListener {
            composeEmail("mail@gmail.com", "Hello!")
            //Toast.makeText(context, "Email", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}