package com.gmail.pavlovsv93.healthysoul.di

import com.gmail.data.data.psychologist.DetailsPsychologistDataSource
import com.gmail.data.data.psychologist.DetailsPsychologistDataSourceInterface
import com.gmail.data.data.psychologist.PsychologistDataSource
import com.gmail.data.data.psychologist.PsychologistDataSourceInterface
import com.gmail.data.repository.psychologist.PsychologistRepository
import com.gmail.data.repository.psychologist.PsychologistRepositoryInterface
import com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.details.PsychologistDetailsViewModel
import com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.psychologistlist.PsychologistViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val PSYCHOLOGIST_VIEW_MODEL = "PsychologistViewModel"
const val DETAILS_PSYCHOLOGIST_VIEW_MODEL = "PsychologistDetailsViewModel"

val psychologistModule = module {
    single<PsychologistRepositoryInterface> { PsychologistRepository(db = get<FirebaseFirestore>()) }
    single<PsychologistDataSourceInterface> {
        PsychologistDataSource(
            repository = get<PsychologistRepositoryInterface>(),
            dao = get()
        )
    }
    viewModel<PsychologistViewModel>(named(PSYCHOLOGIST_VIEW_MODEL)) {
        PsychologistViewModel(dataSource = get<PsychologistDataSourceInterface>())
    }
    single<DetailsPsychologistDataSourceInterface> { DetailsPsychologistDataSource(repository = get<PsychologistRepositoryInterface>()) }
    viewModel<PsychologistDetailsViewModel>(named(DETAILS_PSYCHOLOGIST_VIEW_MODEL)) {
        PsychologistDetailsViewModel(dataSource = get<DetailsPsychologistDataSourceInterface>())
    }
}