package com.gmail.data.repository.questionrepository

import com.gmail.data.entity.tests.questionentity.HintEntity
import com.gmail.data.entity.tests.questionentity.QuestionEntity
import com.gmail.pavlovsv93.healthysoul.utils.GeneralTestData
import kotlinx.coroutines.flow.Flow

interface DataSourceInterface {
	suspend fun getAllTests() : Flow<List<GeneralTestData>>
	suspend fun getQuestion(questionId: String): Flow<QuestionEntity>
	suspend fun getHint(hintId: String): Flow<HintEntity>
}