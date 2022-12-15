package com.gmail.pavlovsv93.healthysoul.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.data.data.favorites.FavoritesDataSourceInterface
import com.gmail.data.data.psychologist.PsychologistDataSourceInterface
import com.gmail.data.data.room.RoomEntity
import com.gmail.data.entity.PsychologistEntity
import com.gmail.pavlovsv93.healthysoul.utils.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val dataSource: FavoritesDataSourceInterface,
    private val stateFlow: MutableStateFlow<AppState> = MutableStateFlow(AppState.OnLoading)
): ViewModel() {

    fun getData() = stateFlow.asStateFlow()

    fun getAllFavorites() = viewModelScope.launch {
        stateFlow.value = AppState.OnLoading
        dataSource.getAllFavorites()
            .catch { exc ->
                stateFlow.value = AppState.OnException(exc)
            }
            .collect { data ->
                stateFlow.value = AppState.OnSuccess(data)
            }
    }

    fun deleteFavorite(psychologist: RoomEntity) = viewModelScope.launch {
        dataSource.deleteFavorite(psychologist)
    }
    fun addFavorite(psychologist: RoomEntity) = viewModelScope.launch {
        dataSource.addFavorite(psychologist)
    }
}