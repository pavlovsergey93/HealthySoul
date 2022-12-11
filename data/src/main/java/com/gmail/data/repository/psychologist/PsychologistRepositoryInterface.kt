package com.gmail.data.repository.psychologist

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface PsychologistRepositoryInterface {
    suspend fun getAllData(): Flow<List<DocumentSnapshot>>
    suspend fun getItemData(idPsychologist: String): Flow<DocumentSnapshot>
}