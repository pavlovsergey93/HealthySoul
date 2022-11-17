package com.gmail.data.repository.questionrepository

import com.gmail.data.entity.tests.questionentity.HintEntity
import com.gmail.data.entity.tests.questionentity.QuestionEntity
import kotlinx.coroutines.flow.Flow

interface DataSourceInterface {
	suspend fun getQuestion(questionId: String): Flow<QuestionEntity>
	suspend fun getHint(hintId: String): Flow<HintEntity>
}