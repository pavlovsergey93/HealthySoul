package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen

sealed class AppState {
    object OnLoading : AppState()
    data class OnSuccess<T>(val success: T) : AppState()
    data class OnException(val exception: Throwable) : AppState()
    data class OnShowMessage(val message: String) : AppState()
}