package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.psychologistlist.adapter

import com.gmail.data.entity.PsychologistEntity

interface ClickedOnPsychologist {
    fun onClick(id: String)
    fun deleteFavorite(psychologist: PsychologistEntity)
    fun addFavorite(psychologist: PsychologistEntity)
}