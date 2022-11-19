package com.gmail.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun getAllData(): Flow<List<DocumentSnapshot>>
    suspend fun getItemData(idPsychologist: String): Flow<DocumentSnapshot>
    suspend fun addData(data: Map<String, Any>): Flow<Any>
}