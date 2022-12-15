package com.gmail.pavlovsv93.healthysoul.di

import androidx.room.Room
import com.gmail.data.data.favorites.FavoritesDataSource
import com.gmail.data.data.favorites.FavoritesDataSourceInterface
import com.gmail.data.data.room.RoomDB
import com.gmail.data.data.room.RoomDao
import com.gmail.pavlovsv93.healthysoul.ui.favorites.FavoritesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val DB_NAME = "favorites"
const val VIEW_MODEL_FAVORITES = "FavoritesViewModel"
val roomModule = module {
    single<RoomDB> {
        Room.databaseBuilder(androidContext(), RoomDB::class.java, DB_NAME)
            .allowMainThreadQueries()
            .build()
    }
    single<RoomDao> { get<RoomDB>().favoriteDao() }
    single<FavoritesDataSourceInterface> { FavoritesDataSource(dao = get()) }
    viewModel<FavoritesViewModel>(named(DETAILS_PSYCHOLOGIST_VIEW_MODEL)){
        FavoritesViewModel(dataSource = get<FavoritesDataSourceInterface>())
    }
}