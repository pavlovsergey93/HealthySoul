package com.gmail.pavlovsv93.healthysoul.ui.psychologist_screen

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.data.entity.PsychologistEntity


class MainFragmentAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private val psychologistList = ArrayList<PsychologistEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(parent)

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
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