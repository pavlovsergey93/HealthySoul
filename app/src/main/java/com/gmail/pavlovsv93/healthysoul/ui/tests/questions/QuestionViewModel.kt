package com.gmail.pavlovsv93.healthysoul.ui.tests.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.data.repository.questionrepository.QuestionsDataSourceInterface
import com.gmail.pavlovsv93.healthysoul.ui.tests.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class QuestionsViewModel(
	private val dataSource: QuestionsDataSourceInterface,
	private val stateFlow: MutableStateFlow<AppState> = MutableStateFlow(AppState.OnLoading)
) : ViewModel() {

	fun getData(): StateFlow<AppState> = stateFlow.asStateFlow()

	fun getQuestion(questionId: String) = viewModelScope.launch(Dispatchers.IO) {
		stateFlow.tryEmit(AppState.OnLoading)
		dataSource.getQuestion(questionId)
			.catch { exc ->
				stateFlow.tryEmit(AppState.OnException(exc))
			}.collect{ question ->
				stateFlow.tryEmit(AppState.OnSuccess(question))
			}
	}
}