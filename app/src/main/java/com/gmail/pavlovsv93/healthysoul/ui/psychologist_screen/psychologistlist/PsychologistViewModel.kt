package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.psychologistlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmai.pavlovsv93.healtysoul.domain.repository.psycholigist.PsychologistDataSourceInterface
import com.gmail.pavlovsv93.healthysoul.utils.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PsychologistViewModel(
    private val dataSource: PsychologistDataSourceInterface,
    private val stateFlow: MutableStateFlow<AppState> = MutableStateFlow(AppState.OnLoading)
) : ViewModel() {

    fun getData() = stateFlow.asStateFlow()

    fun getAllPsychologist() = viewModelScope.launch {
        stateFlow.value = AppState.OnLoading
        dataSource.getAllPsychologistEntity()
            .catch { exc ->
                stateFlow.value = AppState.OnException(exc)
            }
            .collect { data ->
                stateFlow.value = AppState.OnSuccess(data)
            }
    }
}