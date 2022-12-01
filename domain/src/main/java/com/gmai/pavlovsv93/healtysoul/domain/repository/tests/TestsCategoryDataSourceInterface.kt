package com.gmai.pavlovsv93.healtysoul.domain.repository.tests

import com.gmail.pavlovsv93.healthysoul.utils.GeneralTestData
import kotlinx.coroutines.flow.Flow

interface TestsCategoryDataSourceInterface {
	suspend fun getListCategory(): Flow<List<GeneralTestData>>
}