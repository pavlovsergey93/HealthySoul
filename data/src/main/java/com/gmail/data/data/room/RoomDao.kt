package com.gmail.data.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface RoomDao {
    @Insert
    fun insert(vararg items: RoomEntity)

    @Delete
    fun delete(item: RoomEntity)
}