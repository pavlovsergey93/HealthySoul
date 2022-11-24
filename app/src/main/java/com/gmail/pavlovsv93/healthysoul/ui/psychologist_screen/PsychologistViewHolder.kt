package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gmail.data.entity.PsychologistEntity
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.ItemPsychologistBinding

class PsychologistViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_psychologist, parent, false)
) {
    private val binding = ItemPsychologistBinding.bind(itemView)

    fun bind(psychologistEntity: PsychologistEntity) {
        binding.avatarImageView.load(psychologistEntity.avatar)
        binding.nameTextView.text = psychologistEntity.name
        binding.surnameTextView.text = psychologistEntity.surname
        binding.patronymicTextView.text = psychologistEntity.patronymic
    }

}