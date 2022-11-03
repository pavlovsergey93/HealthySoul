package com.gmail.pavlovsv93.healthysoul.ui.tests.questionsadapter.questionentity

data class QuestionEntity(
	val questionId: String,
	val question: String,
	val answers: List<Answer>
) {
	data class Answer(
		val answer: String,
		val nextQuestionId: String?,
		val hintId: String? = null
	)
}
