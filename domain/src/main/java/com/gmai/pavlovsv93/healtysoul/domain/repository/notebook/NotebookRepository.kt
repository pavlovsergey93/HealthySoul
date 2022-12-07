package com.gmai.pavlovsv93.healtysoul.domain.repository.notebook

import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.Notebook
import kotlinx.coroutines.flow.Flow

interface NotebookRepository {
    fun deleteNote(note: Notebook): Flow<Boolean>
    fun getAllNotes(): Flow<List<Notebook>>
    fun getNote(id: Int): Flow<Notebook>
    fun saveNote(note: Notebook): Flow<Boolean>
    fun updateNote(note: Notebook): Flow<Boolean>
}