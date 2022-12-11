package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.psychologistlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.data.data.psychologist.PsychologistDataSourceInterface
import com.gmail.pavlovsv93.healthysoul.utils.AppState
import com.gmail.pavlovsv93.healthysoul.utils.calculatorDistance
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PsychologistViewModel(
    private val dataSource: PsychologistDataSourceInterface,
    private val stateFlow: MutableStateFlow<AppState> = MutableStateFlow(AppState.OnLoading)
) : ViewModel() {

    fun getData() = stateFlow.asStateFlow()

    fun getAllPsychologist(geo: GeoPoint) = viewModelScope.launch {
        stateFlow.value = AppState.OnLoading
        dataSource.getAllPsychologistEntity()
            .map { list ->
                list.sortedBy { it.rating }
                list.sortedBy {
                    calculatorDistance(geo, it.geoPoint)
                }
            }
            .catch { exc ->
                stateFlow.value = AppState.OnException(exc)
            }
            .collect { data ->
                stateFlow.value = AppState.OnSuccess(data)
            }
    }
}