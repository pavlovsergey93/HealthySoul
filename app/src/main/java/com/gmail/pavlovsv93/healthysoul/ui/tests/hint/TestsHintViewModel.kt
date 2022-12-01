package com.gmail.pavlovsv93.healthysoul.ui.tests.hint

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.data.data.tests.hint.HintDataSourceInterface
import com.gmail.pavlovsv93.healthysoul.ui.tests.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TestsHintViewModel(
    private val dataSource: HintDataSourceInterface,
    private val stateFlow: MutableStateFlow<AppState> = MutableStateFlow(AppState.OnLoading)
) : ViewModel() {

    fun getData() : StateFlow<AppState> = stateFlow.asStateFlow()

    fun getHint(hintId : String) = viewModelScope.launch(Dispatchers.IO) {
        stateFlow.tryEmit(AppState.OnLoading)
        dataSource.getHint(hintId)
            .catch { exc ->
                stateFlow.tryEmit(AppState.OnException(exc))
            }.collect{ hint ->
                stateFlow.tryEmit(AppState.OnSuccess(hint))
            }
    }

}