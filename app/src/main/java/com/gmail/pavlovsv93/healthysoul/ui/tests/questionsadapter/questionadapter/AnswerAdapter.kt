package com.gmail.pavlovsv93.healthysoul.ui.tests.questionsadapter.questionadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentTestQuestionItemBinding
import com.gmail.data.entity.tests.questionentity.QuestionEntity

class AnswerAdapter(private val onClick: OnClickOnAnswer) :
	RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {
	private val listAnswers = mutableListOf<QuestionEntity.Answer>()
	fun setData(listData: List<QuestionEntity.Answer>) {
		if (listAnswers.isNotEmpty()) {
			listAnswers.clear()
		}
		listAnswers.addAll(listData)
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
		return AnswerViewHolder(
			LayoutInflater.from(parent.context)
				.inflate(R.layout.fragment_test_question_item, parent, false)
		)
	}

	override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
		holder.bind(listAnswers[position])
	}

	override fun getItemCount(): Int = listAnswers.size
	inner class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(data: QuestionEntity.Answer) {
			val binding = FragmentTestQuestionItemBinding.bind(itemView)
			with(binding) {
				rbAnswerItem.text = data.answer
				rbAnswerItem.setOnClickListener {
					if (!data.nextQuestionId.isNullOrEmpty()) {
						onClick.showNextQuestion(data.nextQuestionId!!)
					} else if (!data.hintId.isNullOrEmpty()) {
						onClick.showHint(data.hintId!!)
					}
				}
			}
		}
	}
}

