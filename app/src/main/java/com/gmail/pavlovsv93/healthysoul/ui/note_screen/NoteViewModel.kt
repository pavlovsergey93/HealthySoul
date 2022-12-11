package com.gmail.pavlovsv93.healthysoul.ui.note_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.NoteDetails
import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.Notebook
import com.gmai.pavlovsv93.healtysoul.domain.usecases.notebook.DeleteNoteUseCase
import com.gmai.pavlovsv93.healtysoul.domain.usecases.notebook.GetNoteUseCase
import com.gmai.pavlovsv93.healtysoul.domain.usecases.notebook.SaveNoteUseCase
import com.gmai.pavlovsv93.healtysoul.domain.usecases.notebook.UpdateNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<NoteDetails> = MutableStateFlow(NoteDetails())
    val state = _state.asStateFlow()

    fun deleteNote(notebook: Notebook) {
        viewModelScope.launch(Dispatchers.Main) {
            deleteNoteUseCase.invoke(notebook).collect {
                _state.value = state.value.copy(
                    isLoadingDone = true, deleteNote = true
                )
            }
        }
    }

    fun getNote(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            getNoteUseCase.invoke(id).collect {
                _state.value = state.value.copy(
                    isLoadingDone = true, getNote = true, note = it
                )
            }
        }
    }

    fun saveNote(notebook: Notebook) {
        viewModelScope.launch(Dispatchers.Main) {
            saveNoteUseCase.invoke(notebook).collect {
                _state.value = state.value.copy(
                    isLoadingDone = true, saveNote = true
                )
            }
        }
    }

    fun updateNote(notebook: Notebook) {
        viewModelScope.launch(Dispatchers.Main) {
            updateNoteUseCase.invoke(notebook).collect {
                _state.value = state.value.copy(
                    isLoadingDone = true, updateNote = true
                )
            }
        }
    }

}