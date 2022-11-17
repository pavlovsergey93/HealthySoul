package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gmail.data.repository.FirebaseRepository
import kotlinx.coroutines.launch
import java.io.IOException

class PsychologistViewModel(val repository: FirebaseRepository) : ViewModel() {

    fun requestAllPsychologist() {

        viewModelScope.launch {
            try {
                repository.getAllData()
            } catch (_: IOException) {
            }

        }
    }
}

class PsychologistViewModelFactory(private val repository: FirebaseRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PsychologistViewModel(repository) as T
}