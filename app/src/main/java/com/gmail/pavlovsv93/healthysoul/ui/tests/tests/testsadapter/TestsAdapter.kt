package com.gmail.pavlovsv93.healthysoul.ui.tests.tests.testsadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentTestsRecyclerviewItemChildBinding
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentTestsRecyclerviewItemParentBinding
import com.gmail.pavlovsv93.healthysoul.utils.*

class TestsAdapter(private val onClick: ClickedOnTest) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private val listData: MutableList<GeneralTestData> = mutableListOf()

	fun setData(data: List<GeneralTestData>){
		if (listData.isNotEmpty()){
			listData.clear()
		}
		listData.addAll(data)
		notifyDataSetChanged()
	}

	inner class ParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		fun bind(data: ParentData) {
			val binding = FragmentTestsRecyclerviewItemParentBinding.bind(itemView)
			with(binding){
				ivIcon.load(data.image){
					crossfade(true)
					placeholder(R.drawable.ic_baseline_category_24)
					transformations(CircleCropTransformation())
				}
				tvTitle.text = data.title
				cvParent.setOnClickListener {
					if (!data.isExpanded && !data.subList.isNullOrEmpty()) {
						data.isExpanded = true
						showSublist(data.subList!!, adapterPosition)
					} else if (data.isExpanded && !data.subList.isNullOrEmpty()) {
						data.isExpanded = false
						hideSublist(data.subList!!, adapterPosition)
					}
				}
			}
		}
	}

	inner class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		fun bind(data: ChildData) {
			val binding = FragmentTestsRecyclerviewItemChildBinding.bind(itemView)
			with(binding) {
				cvChild.setOnClickListener {
					onClick.onClick(data.questionId)
				}
				tvTitleChild.text = data.title
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return when (viewType) {
			PARENT_TYPE -> {
				ParentViewHolder(
					LayoutInflater.from(parent.context).inflate(R.layout.fragment_tests_recyclerview_item_parent, parent, false)
				)
			}
			else -> {
				ChildViewHolder(
					LayoutInflater.from(parent.context).inflate(R.layout.fragment_tests_recyclerview_item_child, parent, false)
				)
			}
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (getItemViewType(position)) {
			PARENT_TYPE -> {
				val dataDisplay = listData[position] as ParentData
				(holder as ParentViewHolder).bind(dataDisplay)
			}
			CHILD_TYPE -> {
				val dataDisplay = listData[position] as ChildData
				(holder as ChildViewHolder).bind(dataDisplay)
			}
		}
	}

	override fun getItemCount(): Int = listData.size

	override fun getItemViewType(position: Int): Int {
		return listData[position].type
	}

	private fun showSublist(list: List<ChildData>, startPosition: Int) {
		val nextPosition = startPosition + 1
		listData.addAll(
			nextPosition,
			list
		)
		notifyItemRangeInserted(nextPosition, list.size)
	}

	private fun hideSublist(list: List<ChildData>, startPosition: Int) {
		val nextPosition = startPosition + 1
		repeat(list.size) {
			listData.removeAt(nextPosition)
			notifyItemRemoved(nextPosition)
		}
	}
}