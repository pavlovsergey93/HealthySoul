package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import coil.transform.CircleCropTransformation
import com.gmail.data.entity.Education
import com.gmail.data.entity.PsychologistEntity
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentPsychologistDetailsBinding
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
        if (savedInstanceState == null) {
            idPsy?.let { viewModel.getDetailsPsychologist(it) }
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
                val psychologist = state.success as PsychologistEntity
                displayDetails(psychologist)
            }
        }
    }

    private fun displayDetails(psychologist: PsychologistEntity) {
        binding.avatarImageViewDetail.load(psychologist.avatar) {
            transformations(CircleCropTransformation())
        }
        binding.nameTextViewDetail.text = psychologist.name
        binding.surnameTextViewDetail.text = psychologist.surname
        binding.patronymicTextViewDetail.text = psychologist.patronymic
        binding.countryTextViewDetail.text = psychologist.country
        binding.cityTextViewDetail.text = psychologist.city
        binding.profileTextViewDetail.text = psychologist.profile

//        binding.titleContactTextViewDetail.text = psychologist.contacts[].titleContact
//        binding.contactTextViewDetail.text = psychologist.contacts[].contact

        binding.experienceTextViewDetail.text = psychologist.experience.toString()
        binding.professionTextViewDetail.text = psychologist.specialization.profession
        binding.specializationListTextViewDetail.text =
            psychologist.specialization.specialization.toString()

//        binding.universityTextViewDetail.text = psychologist.education[0].university
//        binding.yearOfGraduationTextViewDetail.text = psychologist.education[3].yearOfGraduation.toString()
//        binding.facultyTextViewDetail.text = psychologist.education[1].faculty
//        binding.specializationEduTextViewDetail.text = psychologist.education[2].specialization

        binding.ratingTextViewDetail.text = psychologist.rating.toString()
        binding.numberOfVotesTextViewDetail.text = psychologist.numberOfVotes.toString()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}