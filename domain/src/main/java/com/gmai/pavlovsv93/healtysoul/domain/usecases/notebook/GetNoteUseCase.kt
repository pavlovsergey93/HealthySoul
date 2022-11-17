package com.gmai.pavlovsv93.healtysoul.domain.usecases.notebook

import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.Notebook
import com.gmai.pavlovsv93.healtysoul.domain.repository.notebook.NotebookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable

class GetNoteUseCase(private val notebookRepository: NotebookRepository) : (Int) -> Flow<Notebook> {
    override fun invoke(id: Int): Flow<Notebook> = notebookRepository
        .getNote(id)
        .cancellable()
}