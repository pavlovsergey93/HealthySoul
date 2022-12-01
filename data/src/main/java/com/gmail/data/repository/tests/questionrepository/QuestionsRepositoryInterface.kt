package com.gmail.data.repository.tests.questionrepository

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface QuestionsRepositoryInterface {
	suspend fun getQuestion(questionId: String): Flow<DocumentSnapshot>
}