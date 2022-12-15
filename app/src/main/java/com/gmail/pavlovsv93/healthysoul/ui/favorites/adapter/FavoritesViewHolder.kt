package com.gmail.pavlovsv93.healthysoul.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.gmail.data.entity.PsychologistEntity
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.ItemPsychologistBinding

class FavoritesViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_psychologist, parent, false)
) {
    private val binding = ItemPsychologistBinding.bind(itemView)

    fun bind(psychologistEntity: PsychologistEntity) {
        binding.avatarImageView.load(psychologistEntity.avatar) {
            transformations(CircleCropTransformation())
        }
        binding.surnameTextView.text = psychologistEntity.surname
        binding.namePatronymicTextView.text = if(psychologistEntity.patronymic.isNullOrEmpty()) {"${psychologistEntity.name}"} else {"${psychologistEntity.name} ${psychologistEntity.patronymic}"}
        binding.professionTextView.text = psychologistEntity.specialization.profession
        binding.profileTextView.text = psychologistEntity.profile
    }
}