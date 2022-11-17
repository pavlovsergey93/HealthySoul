package com.gmail.pavlovsv93.healthysoul.ui.tests

import kotlin.random.Random

sealed class AppState{
	object OnLoading : AppState()
	data class OnSuccess<T>(val success:T) : AppState()
	data class OnException(val exception: Throwable) : AppState()
	data class OnShowMessage(val message: String): AppState()
	object OnEmpty : AppState()
}
