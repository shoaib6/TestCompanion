package com.example.testcompanion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CheckingAdapter(private val quizQuestions: List<QuizQuestion>, private val checkingActivity: CheckingActivity) :
    RecyclerView.Adapter<CheckingAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val tvQuestions: TextView = itemView.findViewById(R.id.tv_questions)
        val tvOption1: TextView = itemView.findViewById(R.id.tv_option1)
        val tvOption2: TextView = itemView.findViewById(R.id.tv_option2)
        val tvOption3: TextView = itemView.findViewById(R.id.tv_option3)
        val tvOption4: TextView = itemView.findViewById(R.id.tv_option4)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quizQuestion = quizQuestions[position]
        val question = quizQuestions[Constant.universalIndex]

        holder.tvQuestions.text = "Q: ${quizQuestion.question}"
        holder.tvOption1.text = "1. ${quizQuestion.options[0]}"
        holder.tvOption2.text = "2. ${quizQuestion.options[1]}"
        holder.tvOption3.text = "3. ${quizQuestion.options[2]}"
        holder.tvOption4.text = "4. ${quizQuestion.options[3]}"
    }

    override fun getItemCount(): Int {
        return 1
    }

}