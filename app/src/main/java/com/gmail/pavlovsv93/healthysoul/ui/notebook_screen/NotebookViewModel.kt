package com.gmail.pavlovsv93.healthysoul.ui.notebook_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.NotebookDetails
import com.gmai.pavlovsv93.healtysoul.domain.usecases.notebook.GetAllNotesUseCase
import com.gmai.pavlovsv93.healtysoul.domain.wrapper.CaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotebookViewModel(private val getAllNotesUseCase: GetAllNotesUseCase) : ViewModel() {

    private val _state: MutableStateFlow<NotebookDetails> = MutableStateFlow(NotebookDetails())
    val state = _state.asStateFlow()

    fun initialization() {
        viewModelScope.launch(Dispatchers.Main) {
            getAllNotesUseCase.invoke().collect { result ->
                when (result) {
                    is CaseResult.Success -> {
                        _state.value = state.value.copy(
                            isLoadingDone = true,
                            listNotes = result.response
                        )
                    }
                    is CaseResult.Failure -> {
                        Log.e("pie", "initialization: NotebookViewModel//CaseResult.Failure")
                    }
                }
            }
        }
    }
}