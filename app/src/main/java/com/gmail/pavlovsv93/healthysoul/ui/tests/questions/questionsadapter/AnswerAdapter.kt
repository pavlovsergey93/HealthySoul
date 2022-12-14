package com.gmail.pavlovsv93.healthysoul.ui.tests.questions.questionsadapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmai.pavlovsv93.healtysoul.domain.models.tests.QuestionEntity
import com.gmail.pavlovsv93.healthysoul.R
import com.gmail.pavlovsv93.healthysoul.databinding.FragmentTestQuestionItemBinding

class AnswerAdapter(private val onClick: OnClickOnAnswer) :
    RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {
    private val listAnswers = mutableListOf<QuestionEntity.Answer>()

    @SuppressLint("NotifyDataSetChanged")
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
                tvAnswerItem.text = data.answer
                tvAnswerItem.setOnClickListener {
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

