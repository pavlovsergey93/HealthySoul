package com.gmail.data.data.tests.tests

import com.gmai.pavlovsv93.healtysoul.domain.repository.tests.TestsCategoryDataSourceInterface
import com.gmail.data.repository.tests.testscategory.TestsCategoryRepositoryInterface
import com.gmail.pavlovsv93.healthysoul.utils.ChildData
import com.gmail.pavlovsv93.healthysoul.utils.GeneralTestData
import com.gmail.pavlovsv93.healthysoul.utils.ParentData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TestsCategoryDataSource(private val repository: TestsCategoryRepositoryInterface) :
	TestsCategoryDataSourceInterface {
	companion object{
		private const val KEY_TITLE = "title"
		private const val KEY_SUBLIST = "sublist"
		private const val KEY_IMAGE = "image"
		private const val KEY_SUBLIST_TITLE = "titleItem"
		private const val KEY_SUBLIST_ID_QUESTION = "idQuestion"
	}
	override suspend fun getListCategory(): Flow<List<GeneralTestData>> {
		return repository.getListCategory().map { list ->
			list.map { item ->
				val subListResult = mutableListOf<ChildData>()
				val title = item.data?.get(KEY_TITLE) as String
				val image = item.data?.get(KEY_IMAGE) as String
				val subList = item.data?.get(KEY_SUBLIST) as List<Map<String, Any>>
				subList.forEach { itemSublist ->
					val itemSublistTitle = itemSublist[KEY_SUBLIST_TITLE] as String
					val itemSublistIdQuestion = itemSublist[KEY_SUBLIST_ID_QUESTION] as String
					subListResult.add(
						ChildData(
							questionId = itemSublistIdQuestion,
							title = itemSublistTitle
						)
					)
				}
				ParentData(
					title = title,
					subList = subListResult,
					image = image
				)
			}
		}
	}
}