package com.gmail.pavlovsv93.healthysoul.ui.profile

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import java.io.File

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

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCall)
        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    doSomeOperations(data)
                }
            }

        binding.loadPhoto.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            resultLauncher.launch(photoPickerIntent)
        }

        binding.deletePhoto.setOnClickListener {
            Glide.with(binding.profilePhoto.context)
                .load(R.drawable.ic_profile)
                .circleCrop()
                .into(binding.profilePhoto)
            binding.loadPhoto.visibility = View.VISIBLE
            binding.deletePhoto.visibility = View.GONE
        }

        binding.signOut.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext())
            updateUI()
        }
        binding.signDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.dialog_title))
                .setPositiveButton(
                    R.string.dialog_button_positive
                ) { dialog, _ ->
                    AuthUI.getInstance().delete(requireContext())
                    updateUI()
                    dialog.dismiss()
                }.setNegativeButton(R.string.dialog_button_negative) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
        observeAuthenticationState()
    }

    private fun updateUI() {
        val args = Bundle().apply {
            putBoolean(ARGS_SIGN_OUT_OR_DELETE, true)
        }
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(R.id.greetingFragment, args)
    }

    private fun doSomeOperations(data: Intent?) {
        val selectedImageUri = data?.data
        val imagePath = getRealPathFromURI(selectedImageUri)

        Glide.with(binding.profilePhoto.context)
            .load(File(imagePath!!))
            .circleCrop()
            .into(binding.profilePhoto)

        binding.loadPhoto.visibility = View.GONE
        binding.deletePhoto.visibility = View.VISIBLE
    }

    private fun getRealPathFromURI(contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(requireContext(), contentUri!!, proj, null, null, null)
        val cursor: Cursor = loader.loadInBackground()!!
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result: String = cursor.getString(column_index)
        cursor.close()
        return result
    }

    private fun observeAuthenticationState() {
        val image = FirebaseAuth.getInstance().currentUser?.photoUrl
        val name =
            String.format(FirebaseAuth.getInstance().currentUser?.displayName ?: "пользователь")
        val number =
            String.format(FirebaseAuth.getInstance().currentUser?.phoneNumber ?: "")
        val email = String.format(FirebaseAuth.getInstance().currentUser?.email ?: "")
        Glide.with(binding.profilePhoto.context)
            .load(image)
            .circleCrop()
            .into(binding.profilePhoto)
        binding.nameText.text = name
        binding.phoneNumber.text = number
        binding.emailText.text = email

    }

    private val backCall = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    companion object {
        const val ARGS_SIGN_OUT_OR_DELETE = "ARGS_SIGN_OUT_OR_DELETE"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}