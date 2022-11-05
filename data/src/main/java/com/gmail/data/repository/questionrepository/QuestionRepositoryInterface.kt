package com.gmail.data.repository.questionrepository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface QuestionRepositoryInterface {
	suspend fun getAllTests() : Flow<List<DocumentSnapshot>>
	suspend fun getQuestion(questionId: String): Flow<DocumentSnapshot>
	suspend fun getHint(hintId: String): Flow<DocumentSnapshot>
}