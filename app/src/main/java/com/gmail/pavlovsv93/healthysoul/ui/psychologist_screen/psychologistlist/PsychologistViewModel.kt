package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.psychologistlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.data.data.psychologist.PsychologistDataSourceInterface
import com.gmail.data.data.room.RoomEntity
import com.gmail.data.entity.PsychologistEntity
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

    fun deleteFavorite(psychologist: PsychologistEntity) = viewModelScope.launch {
        dataSource.deleteItemPsychologistEntity(createRoomEntity(psychologist))
    }
    fun addFavorite(psychologist: PsychologistEntity) = viewModelScope.launch {
        dataSource.insertItemPsychologistEntity(createRoomEntity(psychologist))
    }
    private fun createRoomEntity(psychologist: PsychologistEntity): RoomEntity =
        RoomEntity(
            primaryKey = psychologist.id,
            avatar = psychologist.avatar,
            name = psychologist.name,
            surname = psychologist.surname,
            patronymic = psychologist.patronymic,
            profession = psychologist.specialization.profession,
            profile = psychologist.profile
        )
}