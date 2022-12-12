package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.gmail.data.entity.PsychologistEntity
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentPsychologistDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PsychologistDetailsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPsychologistDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!
    //val psychologists = mutableListOf<PsychologistEntity>()
    private val args: PsychologistDetailsBottomSheetDialogFragmentArgs by navArgs()

    companion object {
        const val ARG_PHONE = "ARG_PHONE"
        const val ARG_EMAIL = "ARG_EMAIL"
        fun getInstence(
            telephone: String?,
            email: String?
        ): PsychologistDetailsBottomSheetDialogFragment =
            PsychologistDetailsBottomSheetDialogFragment().apply {
                arguments = Bundle().apply {
                    telephone?.let{putString(ARG_PHONE, telephone)}
                    email?.let{putString(ARG_EMAIL, email)}
                }
            }
    }

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
        getContacts()
        displayContacts()
        setIntent()
    }

    private fun getContacts() {
        var telephone: String? = null
        var email: String? = null
        arguments?.let {
            telephone = it.getString(ARG_PHONE)
            email = it.getString(ARG_EMAIL)
        }
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

    private fun composeEmail(address: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$address") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, data)
        }
        if (context?.let { intent.resolveActivity(it.packageManager) } != null) {
            startActivity(intent)
        }
    }

    private fun setIntent() {
        val onClickPhoneButton = binding.TextPhone as TextView
        onClickPhoneButton.setOnClickListener {
            val contact = args.phone
            dialPhoneNumber(contact)
            //Toast.makeText(context, "Phone", Toast.LENGTH_SHORT).show()
        }
        val onClickEmailButton = binding.TextEmailAddress as TextView
        onClickEmailButton.setOnClickListener {
            val contact = args.email
            composeEmail(contact)
            //Toast.makeText(context, "Email", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}