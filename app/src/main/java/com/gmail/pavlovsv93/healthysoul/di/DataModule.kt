package com.gmail.pavlovsv93.healthysoul.di

import com.gmai.pavlovsv93.healtysoul.domain.repository.notebook.NotebookRepository
import com.gmail.data.repository_implementation.notebook.NotebookFirebaseRepositoryImplementation
import org.koin.dsl.module

val dataModule = module {

    single<NotebookRepository> {
        NotebookFirebaseRepositoryImplementation()
    }

//    single<FirebaseRepository> {
//        FirebaseRepository()
//    }
}