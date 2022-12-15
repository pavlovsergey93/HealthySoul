package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen.psychologistlist.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.data.entity.PsychologistEntity
import com.gmail.pavlovsv93.healthysoul.databinding.ItemPsychologistBinding


class PsychologistFragmentAdapter(val onClick: ClickedOnPsychologist) : RecyclerView.Adapter<PsychologistViewHolder>() {

    private val psychologistList = mutableListOf<PsychologistEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PsychologistViewHolder =
        PsychologistViewHolder(parent)

    override fun onBindViewHolder(holder: PsychologistViewHolder, position: Int) {
        holder.bind(psychologistList[position])
        val binding = ItemPsychologistBinding.bind(holder.itemView)
        binding.containerPsychologistItem.setOnClickListener {
            onClick.onClick(psychologistList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return psychologistList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePsychologistList(psychologistList: List<PsychologistEntity>) {
        this.psychologistList.clear()
        this.psychologistList.addAll(psychologistList)
        notifyDataSetChanged()
    }

    fun addFavorite(position: Int){
        onClick.addFavorite(psychologistList[position])
        notifyItemChanged(position)
    }
    fun deleteFavorite(position: Int){
        onClick.deleteFavorite(psychologistList[position])
        notifyItemChanged(position)
    }
}