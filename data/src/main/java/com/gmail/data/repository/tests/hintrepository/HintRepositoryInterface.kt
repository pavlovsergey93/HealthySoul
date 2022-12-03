package com.gmail.data.repository.tests.hintrepository

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface HintRepositoryInterface {
    suspend fun getHint(hintId: String): Flow<DocumentSnapshot>
}