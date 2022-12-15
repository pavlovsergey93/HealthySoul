package com.gmail.pavlovsv93.healthysoul.di

import androidx.room.Room
import com.gmail.data.data.room.RoomDB
import com.gmail.data.data.room.RoomDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
const val DB_NAME = "favorites"
val roomModule = module {
    single<RoomDB> {
        Room.databaseBuilder(androidContext(), RoomDB::class.java, DB_NAME)
            .allowMainThreadQueries()
            .build()
    }
    single<RoomDao> { get<RoomDB>().favoriteDao() }
}