package com.gmail.pavlovsv93.healthysoul.ui.tests.testscategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.data.repository.testscategory.TestsCategoryDataSourceInterface
import com.gmail.pavlovsv93.healthysoul.ui.tests.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TestsViewModel(
	private val dataSource: TestsCategoryDataSourceInterface,
	private val stateFlow: MutableStateFlow<AppState> = MutableStateFlow(AppState.OnLoading(false))
) : ViewModel() {

	fun getData() = stateFlow.asStateFlow()

	fun getTestsCategory() = viewModelScope.launch {
		stateFlow.value = AppState.OnLoading(load = true)
		dataSource.getListCategory()
			.catch { exc ->
				stateFlow.value = AppState.OnException(exc)
			}.collect{ data ->
				stateFlow.value = AppState.OnLoading(false)
				stateFlow.value = AppState.OnSuccess(data)
			}
	}
}