package com.gmail.pavlovsv93.healthysoul.ui.favorites.adapter

import com.gmail.data.data.room.RoomEntity

interface ClickedOnFavorites {
    fun onClick(id: String)
    fun deleteFavorite(psychologist: RoomEntity)
}