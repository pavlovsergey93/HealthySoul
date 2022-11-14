package com.gmail.data.repository.testscategory

import com.gmail.pavlovsv93.healthysoul.utils.GeneralTestData
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface TestsCategoryRepositoryInterface {
	suspend fun getListCategory(): Flow<List<DocumentSnapshot>>
}