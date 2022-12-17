package com.gmail.pavlovsv93.healthysoul.ui.notebook_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.Notebook
import com.gmail.pavlovsv93.healthysoul.databinding.ItemNotebookBinding

class NotebookAdapter(private val listener: Listener) :
    ListAdapter<Notebook, NotebookAdapter.NotebookViewHolder>(ItemCallback),
    View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotebookBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return NotebookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotebookViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            root.tag = item
            headerTitle.text = item.title
        }
    }

    override fun onClick(view: View) {
        val item = view.tag as Notebook
        listener.onChooseNote(item)
    }

    object ItemCallback : DiffUtil.ItemCallback<Notebook>() {
        override fun areItemsTheSame(oldItem: Notebook, newItem: Notebook) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Notebook, newItem: Notebook) = oldItem == newItem

    }

    class NotebookViewHolder(val binding: ItemNotebookBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun onChooseNote(item: Notebook)
    }
}