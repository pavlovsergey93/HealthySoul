package com.gmail.data.data.tests.hint

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface HintRepositoryInterface {
    suspend fun getHint(hintId: String): Flow<DocumentSnapshot>
}