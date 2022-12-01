package com.gmail.data.repository.questionrepository

import com.gmail.data.entity.tests.questionentity.HintEntity
import com.gmail.data.entity.tests.questionentity.QuestionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuestionsDataSource(private val repository: QuestionsRepositoryInterface) :
	QuestionsDataSourceInterface {

	companion object {
		private const val KEY_QUESTION = "question"
		private const val KEY_ANSWERS = "answers"
		private const val KEY_ANSWERS_ANSWER = "answer"
		private const val KEY_ANSWERS_NEXT_QUESTION_ID = "nextQuestionId"
		private const val KEY_ANSWERS_BACK_QUESTION_ID = "backQuestionId"
		private const val KEY_ANSWERS_HINT_ID = "hintId"
	}

	override suspend fun getQuestion(questionId: String): Flow<QuestionEntity> {
		return repository.getQuestion(questionId).map {
			val listAnswer = mutableListOf<QuestionEntity.Answer>()
			val questionId = it.id
			val question = it.data?.get(KEY_QUESTION) as String
			val answers = it.data?.get(KEY_ANSWERS) as List<Map<String, String>>
			answers.forEach { answer ->
				val itemAnswer = answer[KEY_ANSWERS_ANSWER]
				val backQuestionId = answer[KEY_ANSWERS_BACK_QUESTION_ID]
				val nextQuestionId = answer[KEY_ANSWERS_NEXT_QUESTION_ID]
				val hintId = answer[KEY_ANSWERS_HINT_ID]
				val item = QuestionEntity.Answer(
					itemAnswer.toString(),
					backQuestionId,
					nextQuestionId,
					hintId
				)
				listAnswer.add(item)
			}
			QuestionEntity(questionId, question, listAnswer)
		}
	}

}