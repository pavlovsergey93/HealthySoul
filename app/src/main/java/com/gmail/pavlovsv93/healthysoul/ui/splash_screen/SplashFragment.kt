package com.gmail.pavlovsv93.healthysoul.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayoutStates.TAG
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentSplashBinding
import com.gmail.pavlovsv93.healthysoul.ui.authentication.LoginViewModel

class SplashFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentSplashBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSplashBinding.inflate(layoutInflater)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
		view.findViewById<ImageView>(R.id.iv_logotype).animate()
			.rotationBy(720f).setDuration(4000L).start()
        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                viewModel.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
                    when (authenticationState) {
                        LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                            findNavController().navigate(R.id.accountFragment)
                        }
                        LoginViewModel.AuthenticationState.UNAUTHENTICATED -> {
                            findNavController().navigate(R.id.loginFragment)
                        }
                        LoginViewModel.AuthenticationState.INVALID_AUTHENTICATION -> {
                            findNavController().navigate(R.id.loginFragment)
                        }
                    }
                }
            }
        },3000)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    companion object {
        private const val TAG = "SplashFragment"
    }
}

