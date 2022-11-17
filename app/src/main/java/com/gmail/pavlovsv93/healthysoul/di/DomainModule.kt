package com.gmail.pavlovsv93.healthysoul.di

import com.gmai.pavlovsv93.healtysoul.domain.usecases.notebook.*
import org.koin.dsl.module

val domainModule = module {

    factory<DeleteNoteUseCase> {
        DeleteNoteUseCase(notebookRepository = get())
    }

    factory<GetAllNotesUseCase> {
        GetAllNotesUseCase(notebookRepository = get())
    }

    factory<GetNoteUseCase> {
        GetNoteUseCase(notebookRepository = get())
    }

    factory<SaveNoteUseCase> {
        SaveNoteUseCase(notebookRepository = get())
    }

    factory<UpdateNoteUseCase> {
        UpdateNoteUseCase(notebookRepository = get())
    }
}