package com.gmail.pavlovsv93.healthysoul.di

import com.google.firebase.firestore.FirebaseFirestore
import com.gmail.pavlovsv93.healthysoul.ui.notebook_screen.NotebookViewModel
import com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.PsychologistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
	single<FirebaseFirestore> { FirebaseFirestore.getInstance() }

    viewModel<NotebookViewModel> {
        NotebookViewModel(getAllNotesUseCase = get())
    }

    viewModel<PsychologistViewModel> {
        PsychologistViewModel(repository = get())
    }
}