package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.data.entity.PsychologistEntity


class PsychologistFragmentAdapter : RecyclerView.Adapter<PsychologistViewHolder>() {

    private val psychologistList = mutableListOf<PsychologistEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PsychologistViewHolder =
        PsychologistViewHolder(parent)

    override fun onBindViewHolder(holder: PsychologistViewHolder, position: Int) {
        holder.bind(psychologistList[position])
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
}