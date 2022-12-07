package com.gmail.pavlovsv93.healthysoul.di

import com.gmail.pavlovsv93.healthysoul.ui.note_screen.NoteViewModel
import com.gmail.pavlovsv93.healthysoul.ui.notebook_screen.NotebookViewModel
import com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.PsychologistViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
	single<FirebaseFirestore> { FirebaseFirestore.getInstance() }

    viewModel<NotebookViewModel> {
        NotebookViewModel(getAllNotesUseCase = get())
    }

//    viewModel<PsychologistViewModel> {
//        PsychologistViewModel(repository = get())
//    }

    viewModel<NoteViewModel> {
        NoteViewModel(
            deleteNoteUseCase = get(),
            getNoteUseCase = get(),
            saveNoteUseCase = get(),
            updateNoteUseCase = get()
        )
    }

}