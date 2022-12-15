package com.gmail.pavlovsv93.healthysoul.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.data.data.psychologist.DetailsPsychologistDataSourceInterface
import com.gmail.pavlovsv93.healthysoul.utils.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel(
    private val dataSource: DetailsPsychologistDataSourceInterface,
    private val stateFlow: MutableStateFlow<AppState> = MutableStateFlow(AppState.OnLoading)
) : ViewModel() {
    fun getData(): StateFlow<AppState> = stateFlow.asStateFlow()

    fun getAllFavorites() = viewModelScope.launch(Dispatchers.IO) {
        stateFlow.tryEmit(AppState.OnLoading)
        dataSource.getDetailsPsychologist(id)
            .catch { exp ->
                withContext(Dispatchers.Main){
                    stateFlow.tryEmit(AppState.OnException(exp))
                }
            }.collect { psychologist ->
                withContext(Dispatchers.Main){
                    stateFlow.tryEmit(AppState.OnSuccess(psychologist))
                }
            }
    }
}