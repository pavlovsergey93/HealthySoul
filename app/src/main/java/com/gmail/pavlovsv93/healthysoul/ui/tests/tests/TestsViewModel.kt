package com.gmail.pavlovsv93.healthysoul.ui.tests.tests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.data.repository.testscategory.TestsCategoryDataSourceInterface
import com.gmail.pavlovsv93.healthysoul.ui.tests.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TestsViewModel(
	private val dataSource: TestsCategoryDataSourceInterface,
	private val stateFlow: MutableStateFlow<AppState> = MutableStateFlow(AppState.OnEmpty)
) : ViewModel() {

	fun getData() = stateFlow.asStateFlow()

	fun getTestsCategory() = viewModelScope.launch {
		stateFlow.tryEmit(AppState.OnLoading)
		dataSource.getListCategory()
			.catch { exc ->
				stateFlow.tryEmit(AppState.OnException(exc))
			}.collect { data ->
				stateFlow.tryEmit(AppState.OnSuccess(data))
			}
	}
}