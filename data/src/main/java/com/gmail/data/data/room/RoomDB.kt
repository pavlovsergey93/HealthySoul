package com.gmail.data.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomEntity::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun favoriteDao(): RoomDao
}