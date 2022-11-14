package com.gmail.data.repository.testscategory

import com.gmail.pavlovsv93.healthysoul.utils.GeneralTestData
import kotlinx.coroutines.flow.Flow

interface TestsCategoryDataSourceInterface {
	suspend fun getListCategory(): Flow<List<GeneralTestData>>
}