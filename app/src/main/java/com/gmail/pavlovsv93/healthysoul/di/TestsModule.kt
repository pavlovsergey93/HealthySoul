package com.gmail.pavlovsv93.healthysoul.di

import com.gmail.data.data.tests.hint.HintDataSource
import com.gmail.data.data.tests.hint.HintDataSourceInterface
import com.gmail.data.data.tests.hint.HintRepository
import com.gmail.data.data.tests.hint.HintRepositoryInterface
import com.gmail.data.repository.questionrepository.QuestionsDataSource
import com.gmail.data.repository.questionrepository.QuestionsDataSourceInterface
import com.gmail.data.repository.questionrepository.QuestionsRepository
import com.gmail.data.repository.questionrepository.QuestionsRepositoryInterface
import com.gmail.data.repository.testscategory.TestsCategoryDataSource
import com.gmail.data.repository.testscategory.TestsCategoryDataSourceInterface
import com.gmail.data.repository.testscategory.TestsCategoryRepository
import com.gmail.data.repository.testscategory.TestsCategoryRepositoryInterface
import com.gmail.pavlovsv93.healthysoul.ui.tests.hint.TestsHintViewModel
import com.gmail.pavlovsv93.healthysoul.ui.tests.questions.QuestionsViewModel
import com.gmail.pavlovsv93.healthysoul.ui.tests.tests.TestsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val TESTS_VIEW_MODEL = "TestsViewModel"
const val QUESTION_VIEW_MODEL = "QuestionsViewModel"
const val HINT_VIEW_MODEL = "TestsHintViewModel"
val testsModule = module {
	single<TestsCategoryRepositoryInterface> { TestsCategoryRepository(db = get<FirebaseFirestore>()) }
	single<TestsCategoryDataSourceInterface> { TestsCategoryDataSource(repository = get<TestsCategoryRepositoryInterface>()) }
	viewModel(named(TESTS_VIEW_MODEL)) {
		TestsViewModel(dataSource = get<TestsCategoryDataSourceInterface>())
	}

	single<QuestionsRepositoryInterface> { QuestionsRepository(db = get<FirebaseFirestore>()) }
	single<QuestionsDataSourceInterface>{ QuestionsDataSource(repository = get<QuestionsRepositoryInterface>()) }
	viewModel(named(QUESTION_VIEW_MODEL)) { QuestionsViewModel(dataSource = get<QuestionsDataSourceInterface>()) }

	single<HintRepositoryInterface> { HintRepository(db = get<FirebaseFirestore>()) }
	single<HintDataSourceInterface> { HintDataSource(repository = get<HintRepositoryInterface>()) }
	viewModel(named(HINT_VIEW_MODEL)) { TestsHintViewModel(dataSource = get<HintDataSourceInterface>()) }
}