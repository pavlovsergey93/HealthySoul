package com.gmail.pavlovsv93.healthysoul.ui.greeting_screen

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentGreetingBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

const val ARG_ACCOUNT_EMAIL = "ARG_ACCOUNT_EMAIL"
class GreetingFragment : Fragment() {

	private var _binding: FragmentGreetingBinding? = null
	private val binding get() = _binding!!

	private lateinit var auth: FirebaseAuth
	private lateinit var oneTapClient: GoogleSignInClient

	override fun onStart() {
		auth = FirebaseAuth.getInstance()
		val currentUser = auth.currentUser
		if (currentUser == null) {
			Toast.makeText(requireContext(), "currentUser NULL", Toast.LENGTH_SHORT).show()
		} else {

		}
		super.onStart()
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGreetingBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		oneTapClient = initClient()
		initClick()
	}

	private fun initClient() : GoogleSignInClient {
		val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestIdToken(getString(R.string.default_web_client_id))
			.requestEmail()
			.build()
		return GoogleSignIn.getClient(requireActivity(), gso)
	}
	
	private val launcher =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
			if (result.resultCode == Activity.RESULT_OK) {
				val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
				if (task.isSuccessful) {
					task.result?.let { account ->
						updateUI(account)
					}
				} else {
					val message = task.exception?.message.toString() ?: "Error"
					Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
				}
			}
		}

	private fun updateUI(account: GoogleSignInAccount) {
		val credential = GoogleAuthProvider.getCredential(account.idToken, null)
		auth.signInWithCredential(credential).addOnCompleteListener { task ->
			if (task.isSuccessful) {
				// todo передать необходимые сведенья из аккаунта
				val data = Bundle().apply {
					putString(ARG_ACCOUNT_EMAIL, account.email)
				}
				findNavController().navigate(GreetingFragmentDirections.actionGreetingFragmentToTabsFragment())
			} else {
				val message = task.exception?.message.toString() ?: "Error"
				Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
			}
		}
	}

	private fun initClick() {
		binding.loginButton.setOnClickListener {
			val intent = oneTapClient.signInIntent
			launcher.launch(intent)
		}
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