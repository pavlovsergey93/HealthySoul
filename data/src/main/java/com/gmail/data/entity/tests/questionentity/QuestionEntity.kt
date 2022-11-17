package com.gmail.data.entity.tests.questionentity

data class QuestionEntity(
	val questionId: String,
	val question: String,
	val answers: List<Answer>
) {
	data class Answer(
		val answer: String,
		val backQuestionId: String?,
		val nextQuestionId: String?,
		val hintId: String? = null
	)
}
