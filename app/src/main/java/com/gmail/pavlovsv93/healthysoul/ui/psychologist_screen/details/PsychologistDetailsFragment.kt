package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.details

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
import coil.load
import coil.transform.CircleCropTransformation
import com.gmail.data.entity.PsychologistEntity
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentPsychologistDetailsBinding
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentPsychologistDetailsEducationBinding
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentPsychologistDetailsSpecialisationBinding
import com.gmail.pavlovsv93.healthysoul.di.DETAILS_PSYCHOLOGIST_VIEW_MODEL
import com.gmail.pavlovsv93.healthysoul.utils.AppState
import com.gmail.pavlovsv93.healthysoul.utils.showMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class PsychologistDetailsFragment : Fragment() {
    companion object {
        const val ARG_ID_PSYCHOLOGIST = "ARG_ID_PSYCHOLOGIST"
    }

    private var _binding: FragmentPsychologistDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PsychologistDetailsViewModel by viewModel(
        named(DETAILS_PSYCHOLOGIST_VIEW_MODEL)
    )
    private var idPsy: String? = null
    private var psy: PsychologistEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPsychologistDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCall)
        getIdPsychologist()
        lifecycleScope.launch(Dispatchers.IO) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getState().collect { state ->
                    withContext(Dispatchers.Main) {
                        ranger(state)
                    }
                }
            }
        }
        initFabContacts()
        if (savedInstanceState == null) {
            idPsy?.let { viewModel.getDetailsPsychologist(it) }
        }
    }

    private fun initFabContacts() {
        binding.fabContactsDetails.setOnClickListener {
            var phone = ""
            var email = ""

            psy?.contacts?.forEach {
                if (it.titleContact == "Телефон") {
                    phone = it.contact
                } else {
                    email = it.contact
                }
            }

            findNavController().navigate(
                PsychologistDetailsFragmentDirections
                    .actionPsychologistDetailsFragmentToPsychologistDetailsBottomSheetDialogFragment(
                        phone = phone,
                        email = email
                    )
            )
        }
    }

    private fun getIdPsychologist() {
        idPsy = arguments?.getString(ARG_ID_PSYCHOLOGIST)
    }

    private fun ranger(state: AppState) {
        when (state) {
            is AppState.OnException -> {
                binding.progressDetails.visibility = View.GONE
                val message = state.exception.message.toString()
                binding.root.showMessage(
                    str = message,
                    actionText = getString(R.string.reload),
                    action = {
                        idPsy?.let { viewModel.getDetailsPsychologist(it) }
                    })
            }
            AppState.OnLoading -> {
                binding.progressDetails.visibility = View.VISIBLE
            }
            is AppState.OnShowMessage -> {
                binding.progressDetails.visibility = View.GONE
                binding.root.showMessage(state.message)
            }
            is AppState.OnSuccess<*> -> {
                binding.progressDetails.visibility = View.GONE
                psy = state.success as PsychologistEntity
                psy?.let { displayDetails(it) }
            }
        }
    }

    private fun displayDetails(psychologist: PsychologistEntity) {
        with(binding) {
            avatarImageViewDetail.load(psychologist.avatar) {
                transformations(CircleCropTransformation())
            }
            countryTextViewDetails.text = psychologist.country
            cityTextViewDetails.text = psychologist.city
            surnameTextViewDetails.text = psychologist.surname
            nameTextViewDetails.text = if (psychologist.patronymic.isNullOrEmpty()) {
                psychologist.name
            } else {
                "${psychologist.name} ${psychologist.patronymic}"
            }
            specialisationTextViewDetails.text = psychologist.specialization.profession
            profileTextViewDetails.text = psychologist.profile
            rating.rating = psychologist.rating
            val containerView = binding.containerViewDetails
            if (psychologist.specialization.specialization.isNotEmpty()) {
                psychologist.specialization.specialization.forEach { item ->
                    val specializationView = LayoutInflater.from(requireContext()).inflate(
                        R.layout.fragment_psychologist_details_specialisation,
                        containerView,
                        false
                    ) as View
                    val bindingSpecialization =
                        FragmentPsychologistDetailsSpecialisationBinding.bind(specializationView)
                    bindingSpecialization.titleSpecialisationItem.text = item
                    containerView.addView(specializationView)
                }
            }
            if (psychologist.education.isNotEmpty()) {
                psychologist.education.forEach { education ->
                    val educationView = LayoutInflater.from(requireContext()).inflate(
                        R.layout.fragment_psychologist_details_education,
                        containerView,
                        false
                    ) as View
                    val bindingEducation =
                        FragmentPsychologistDetailsEducationBinding.bind(educationView)
                    bindingEducation.univercityTextViewImpl.text = education.university
                    bindingEducation.facultyTextViewImpl.text = education.faculty
                    bindingEducation.specialisationTextViewImpl.text = education.specialization
                    bindingEducation.yearTextViewImpl.text = education.yearOfGraduation.toString()
                    containerView.addView(educationView)
                }
            }
        }
    }

    private val backCall = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}