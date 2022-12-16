package com.gmail.pavlovsv93.healthysoul.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.gmail.data.data.room.RoomEntity
import com.gmail.pavlovsv93.healthysoul.databinding.ItemPsychologistBinding

class FavoritesAdapter(private val clickedOnPsychologist: ClickedOnFavorites) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {
    fun submitListDiseases(newList: List<RoomEntity>) {
        list.submitList(newList)
    }

    private val list = AsyncListDiffer(this@FavoritesAdapter, CallbackDiffUtilsItem())

    inner class CallbackDiffUtilsItem : DiffUtil.ItemCallback<RoomEntity>() {
        override fun areItemsTheSame(oldItem: RoomEntity, newItem: RoomEntity): Boolean =
            oldItem.primaryKey == newItem.primaryKey

        override fun areContentsTheSame(oldItem: RoomEntity, newItem: RoomEntity): Boolean =
            oldItem.name == newItem.name
                    && oldItem.avatar == newItem.avatar
                    && oldItem.surname == newItem.surname
                    && oldItem.patronymic == newItem.patronymic
                    && oldItem.profession == newItem.profession
                    && oldItem.profile == newItem.profile
    }

    inner class FavoritesViewHolder(private val binding: ItemPsychologistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(entity: RoomEntity) {
            binding.profileTextView.text = entity.profile
            binding.surnameTextView.text = entity.surname
            binding.namePatronymicTextView.text = if (entity.patronymic.isNullOrEmpty()) {
                entity.name
            } else {
                entity.name + " " + entity.patronymic
            }
            binding.avatarImageView.load(entity.avatar) {
                transformations(CircleCropTransformation())
            }
            binding.professionTextView.text = entity.profession
            binding.cardViewPsychologistItem.setOnClickListener {
                clickedOnPsychologist.onClick(entity.primaryKey)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder =
        FavoritesViewHolder(
            ItemPsychologistBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(list.currentList[position])
    }

    override fun getItemCount(): Int = list.currentList.size
}