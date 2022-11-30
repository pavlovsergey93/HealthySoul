package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.data.entity.DataSourceInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PsychologistViewModel(
    private val dataSource: DataSourceInterface,
    private val stateFlow: MutableStateFlow<AppState> = MutableStateFlow(AppState.OnLoading(false))
) : ViewModel() {

    fun getData() = stateFlow.asStateFlow()

    fun getAllPsychologist() = viewModelScope.launch {
        stateFlow.value = AppState.OnLoading(load = true)
        dataSource.getAllPsychologistEntity()
           // .catch { exc ->
            //    stateFlow.value = AppState.OnException(exc)
          //  }.collect { data ->
            .collect {   data ->
        stateFlow.value = AppState.OnLoading(false)
                stateFlow.value = AppState.OnSuccess(data)
            }
    }
}