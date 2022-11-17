package com.gmail.data.repository.questionrepository

import com.gmail.data.entity.tests.questionentity.HintEntity
import com.gmail.data.entity.tests.questionentity.QuestionEntity
import kotlinx.coroutines.flow.Flow

class QuestionsDataSource(private val repository: QuestionsRepositoryInterface) :
	QuestionsDataSourceInterface {

	companion object{
		private const val KEY_HINT = "hint"
		private const val KEY_QUESTION = "question"
		private const val KEY_ANSWERS = "answers"
		private const val KEY_ANSWERS_ANSWER = "answer"
		private const val KEY_ANSWERS_NEXT_QUESTION_ID = "nextQuestionId"
		private const val KEY_ANSWERS_HINT_ID = "hintId"
	}
	override suspend fun getQuestion(questionId: String): Flow<QuestionEntity> {
		TODO("Not yet implemented")
	}

	override suspend fun getHint(hintId: String): Flow<HintEntity> {
		TODO("Not yet implemented")
	}
}