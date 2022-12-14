package com.gmail.data.repository.tests.testscategory

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface TestsCategoryRepositoryInterface {
    suspend fun getListCategory(): Flow<List<DocumentSnapshot>>
}