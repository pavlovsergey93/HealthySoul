package com.gmail.data.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomEntity(
    @PrimaryKey(autoGenerate = false)
    val primaryKey: String,

    @ColumnInfo(name = "avatar")
    var avatar: String,

    @ColumnInfo(name = "surname")
    var surname: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "patronymic")
    val patronymic: String?,

    @ColumnInfo(name = "profession")
    var profession: String,

    @ColumnInfo(name = "profile")
    var profile: String
)