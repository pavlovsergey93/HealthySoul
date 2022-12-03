package com.gmail.pavlovsv93.healthysoul.ui.greeting_screen

import android.os.Bundle
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"

class LoginFragment : Fragment() {
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_PARAM1)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, name)
                }
            }
    }
}