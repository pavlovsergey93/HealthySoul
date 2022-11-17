package com.gmail.pavlovsv93.healthysoul.ui.tests.questions.questionsadapter

interface OnClickOnAnswer {
	fun showNextQuestion(id: String)
	fun showBackQuestion(id: String)
	fun showHint(id: String)
}