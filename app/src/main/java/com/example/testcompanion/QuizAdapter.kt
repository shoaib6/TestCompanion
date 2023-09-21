package com.example.testcompanion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuizAdapter(private val quizQuestions: List<QuizQuestion>) :
    RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.tv_questions)
        val optionTextView1: TextView = itemView.findViewById(R.id.tv_options1)
        val optionTextView2: TextView = itemView.findViewById(R.id.tv_options2)
        val optionTextView3: TextView = itemView.findViewById(R.id.tv_options3)
        val optionTextView4: TextView = itemView.findViewById(R.id.tv_options4)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quizQuestion = quizQuestions[position]

        holder.questionTextView.text = "Q: ${quizQuestion.question}"
        holder.optionTextView1.text = "1. ${quizQuestion.options[0]}"
        holder.optionTextView2.text = "2. ${quizQuestion.options[1]}"
        holder.optionTextView3.text = "3. ${quizQuestion.options[2]}"
        holder.optionTextView4.text = "4. ${quizQuestion.options[3]}"

    }

    override fun getItemCount(): Int {
        return quizQuestions.size
    }

}