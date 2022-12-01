package com.gmail.data.repository.questionrepository

import com.gmail.data.entity.tests.questionentity.QuestionEntity
import kotlinx.coroutines.flow.Flow

interface QuestionsDataSourceInterface {
	suspend fun getQuestion(questionId: String): Flow<QuestionEntity>
}