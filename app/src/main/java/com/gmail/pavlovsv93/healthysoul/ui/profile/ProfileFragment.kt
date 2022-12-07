package com.gmail.pavlovsv93.healthysoul.ui.profile

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import java.io.File


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    val GALLERY_REQUEST = 1

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

//        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val data: Intent? = result.data
//                doSomeOperations()
//            }
//        }

        binding.loadPhoto.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
//            resultLauncher.launch(photoPickerIntent)
            startActivityForResult(Intent.createChooser(photoPickerIntent, "Выберите изображение"), GALLERY_REQUEST);

        }
        observeAuthenticationState()
    }

    private fun doSomeOperations() {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val selectedImageUri = data.data
            val imagePath = getRealPathFromURI(selectedImageUri)
            Picasso.with(requireContext())
                .load(File(imagePath))
                .resize(200, 200)
                .centerCrop()
                .into(binding.profilePhoto)
        }
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