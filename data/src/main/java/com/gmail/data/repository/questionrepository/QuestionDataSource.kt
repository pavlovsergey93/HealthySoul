package com.gmail.data.repository.questionrepository

import com.gmail.data.entity.tests.questionentity.HintEntity
import com.gmail.data.entity.tests.questionentity.QuestionEntity
import com.gmail.pavlovsv93.healthysoul.utils.GeneralTestData
import kotlinx.coroutines.flow.Flow

class QuestionDataSource: DataSourceInterface {
	override suspend fun getAllTests(): Flow<List<GeneralTestData>> {
		TODO("Not yet implemented")
	}

	override suspend fun getQuestion(questionId: String): Flow<QuestionEntity> {
		TODO("Not yet implemented")
	}

	override suspend fun getHint(hintId: String): Flow<HintEntity> {
		TODO("Not yet implemented")
	}
}