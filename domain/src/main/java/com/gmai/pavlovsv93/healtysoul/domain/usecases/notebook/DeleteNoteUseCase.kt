package com.gmai.pavlovsv93.healtysoul.domain.usecases.notebook

import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.Notebook
import com.gmai.pavlovsv93.healtysoul.domain.repository.notebook.NotebookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable

class DeleteNoteUseCase(private val notebookRepository: NotebookRepository) :
        (Notebook) -> Flow<Boolean> {
    override fun invoke(note: Notebook): Flow<Boolean> = notebookRepository
        .deleteNote(note)
        .cancellable()
}