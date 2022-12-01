package com.gmail.pavlovsv93.healthysoul.ui.tests.hint

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.data.data.tests.hint.HintDataSourceInterface
import com.gmail.data.repository.questionrepository.QuestionsDataSourceInterface
import com.gmail.pavlovsv93.healthysoul.App
import com.gmail.pavlovsv93.healthysoul.ui.tests.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TestsHintViewModel(
    private val dataSource: HintDataSourceInterface,
    private val stateFlow: MutableStateFlow<AppState> = MutableStateFlow(AppState.OnEmpty)
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