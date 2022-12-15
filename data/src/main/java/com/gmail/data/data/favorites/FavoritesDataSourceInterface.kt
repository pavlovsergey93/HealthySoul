package com.gmail.data.data.favorites

import com.gmail.data.data.room.RoomEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesDataSourceInterface {
    suspend fun getAllFavorites(): Flow<List<RoomEntity>>
    suspend fun addFavorite(entity: RoomEntity)
    suspend fun deleteFavorite(entity: RoomEntity)
}