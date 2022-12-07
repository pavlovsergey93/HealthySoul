package com.gmai.pavlovsv93.healtysoul.domain.usecases.notebook

import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.Notebook
import com.gmai.pavlovsv93.healtysoul.domain.repository.notebook.NotebookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable

class GetAllNotesUseCase(
    private val notebookRepository: NotebookRepository
) : () -> Flow<List<Notebook>> {
    override fun invoke(): Flow<List<Notebook>> =
        notebookRepository
            .getAllNotes()
            .cancellable()
}



