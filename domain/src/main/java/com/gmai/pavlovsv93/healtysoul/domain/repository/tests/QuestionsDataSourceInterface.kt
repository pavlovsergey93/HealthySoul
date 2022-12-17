package com.gmai.pavlovsv93.healtysoul.domain.repository.tests

import com.gmai.pavlovsv93.healtysoul.domain.models.tests.QuestionEntity
import kotlinx.coroutines.flow.Flow

interface QuestionsDataSourceInterface {
    suspend fun getQuestion(questionId: String): Flow<QuestionEntity>
}