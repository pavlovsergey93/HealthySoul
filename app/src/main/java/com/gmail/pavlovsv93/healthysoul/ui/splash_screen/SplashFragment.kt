package com.gmail.pavlovsv93.healthysoul.ui.splash_screen

import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gmail.pavlovsv93.healthysoul.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        view.findViewById<ImageView>(R.id.iv_logotype).animate()
            .rotationBy(720f).setDuration(4000L).start()
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            CoroutineScope(Dispatchers.Main).launch {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToGreetingFragment())
            }
        }
    }
}

