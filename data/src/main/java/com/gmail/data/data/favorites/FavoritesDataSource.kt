package com.gmail.data.data.favorites

import com.gmail.data.data.room.RoomDao
import com.gmail.data.data.room.RoomEntity
import kotlinx.coroutines.flow.Flow

class FavoritesDataSource(private val dao: RoomDao) : FavoritesDataSourceInterface {
    override suspend fun getAllFavorites(): Flow<List<RoomEntity>> {
        return dao.getAll()
    }

    override suspend fun addFavorite(entity: RoomEntity) {
        dao.insert(entity)
    }

    override suspend fun deleteFavorite(entity: RoomEntity) {
        dao.delete(entity)
    }
}