package com.gmail.data.repository.questionrepository

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface QuestionsRepositoryInterface {
	suspend fun getQuestion(questionId: String): Flow<DocumentSnapshot>
	suspend fun getHint(hintId: String): Flow<DocumentSnapshot>
}