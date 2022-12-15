package com.gmail.pavlovsv93.healthysoul.ui.favorites.adapter

import com.gmail.data.entity.PsychologistEntity

interface ClickedOnFavorites {
    fun onClick(id: String)
    fun deleteFavorite(psychologist: PsychologistEntity)
    fun addFavorite(psychologist: PsychologistEntity)
}