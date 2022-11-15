package com.gmail.pavlovsv93.healthysoul.ui.tests

sealed class AppState{
	data class OnLoading(val load: Boolean) : AppState()
	data class OnSuccess<T>(val success:T) : AppState()
	data class OnException(val exception: Throwable) : AppState()
	data class OnShowMessage(val message: String): AppState()
}
