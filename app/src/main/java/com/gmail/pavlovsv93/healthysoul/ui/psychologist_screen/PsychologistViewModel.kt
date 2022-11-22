package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gmail.data.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class PsychologistViewModel(val repository: FirebaseRepository) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _data: MutableStateFlow<List<Any>?> = MutableStateFlow(null)
    val data: Flow<List<Any>?> = _data

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error


    fun requestAllPsychologist() {

        _loading.value = true

        viewModelScope.launch {
            try {
                val allData = repository.getAllData()
                _data.emit(listOf(allData))
            } catch (_: IOException) {
                _error.emit("Network error")
            }
            _loading.emit(false)

        }
    }
}