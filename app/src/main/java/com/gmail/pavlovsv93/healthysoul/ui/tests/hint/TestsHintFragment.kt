package com.gmail.pavlovsv93.healthysoul.ui.tests.hint

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.gmai.pavlovsv93.healtysoul.domain.models.tests.HintEntity
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentTestsHintBinding
import com.gmail.pavlovsv93.healthysoul.di.HINT_VIEW_MODEL
import com.gmail.pavlovsv93.healthysoul.utils.AppState
import com.gmail.pavlovsv93.healthysoul.utils.showMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class TestsHintFragment : Fragment() {

    companion object {
        const val ARG_HINT_ID = "ARG_HINT_ID.TestsHintFragment"
    }

    private var _binding: FragmentTestsHintBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TestsHintViewModel by viewModel(named(HINT_VIEW_MODEL))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestsHintBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCall)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getData()
                    .collect { state ->
                        withContext(Dispatchers.Main) {
                            ranger(state)
                        }
                    }
            }
        }
        val hintId = arguments?.getString(ARG_HINT_ID)
        hintId?.let { viewModel.getHint(it) }
        callbackButton()

    }

    private fun callbackButton() {
        binding.btnHome.setOnClickListener {
            findNavController().popBackStack(R.id.testsFragment, false)
        }
    }

    private fun ranger(state: AppState) {
        when (state) {
            is AppState.OnException -> {
                binding.pbProgress.visibility = View.GONE
                binding.root.showMessage(state.exception.message.toString())
            }
            AppState.OnLoading -> {
                binding.pbProgress.visibility = View.VISIBLE
            }
            is AppState.OnShowMessage -> {
                binding.pbProgress.visibility = View.GONE
                binding.root.showMessage(state.message)
            }
            is AppState.OnSuccess<*> -> {
                binding.pbProgress.visibility = View.GONE
                val hint = state.success as HintEntity
                binding.tvHint.text = hint.hint
            }
        }
    }

    private val backCall = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack(
                destinationId = R.id.testsFragment,
                inclusive = true,
                saveState = false
            )
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}