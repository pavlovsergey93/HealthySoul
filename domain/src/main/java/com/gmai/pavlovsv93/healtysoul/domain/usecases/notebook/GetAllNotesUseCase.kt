package com.gmai.pavlovsv93.healtysoul.domain.usecases.notebook

import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.Notebook
import com.gmai.pavlovsv93.healtysoul.domain.repository.notebook.NotebookRepository
import com.gmai.pavlovsv93.healtysoul.domain.usecases.base.DefaultUseCase
import com.gmai.pavlovsv93.healtysoul.domain.wrapper.CaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetAllNotesUseCase(
    private val notebookRepository: NotebookRepository
) : DefaultUseCase<List<Notebook>, Exception> {
    override fun invoke(): Flow<CaseResult<List<Notebook>, Exception>> = flow {
        val result = notebookRepository.getAllNotes()
        emit(result)
    }.map {
        CaseResult.Success(it)
    }
}



