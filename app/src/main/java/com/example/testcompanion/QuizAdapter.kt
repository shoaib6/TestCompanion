package com.example.testcompanion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class QuizAdapter(private val quizQuestions: List<QuizQuestion>, private val quizActivity: QuizActivity) :
    RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    private fun correctOption(selectedOption: Int, tvOption: TextView, quizQuestion: QuizQuestion) : Boolean{
        Constant.flag = true
        return if (selectedOption==quizQuestion.answer.toInt()){
            tvOption.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
            false
        }else{
            tvOption.background = quizActivity.resources.getDrawable(R.drawable.wrong_option_design)
            true
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

        holder.tvQuestions.text = "Q: ${quizQuestion.question}"
        holder.tvOption1.text = "1. ${quizQuestion.options[0]}"
        holder.tvOption2.text = "2. ${quizQuestion.options[1]}"
        holder.tvOption3.text = "3. ${quizQuestion.options[2]}"
        holder.tvOption4.text = "4. ${quizQuestion.options[3]}"

        holder.tvOption1.setOnClickListener {
            if (!Constant.flag){
                val wrongOptionSelected = correctOption(1,holder.tvOption1,quizQuestion)
                if (wrongOptionSelected){
                    if (quizQuestion.answer.toInt()==1){
                        holder.tvOption1.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }else if (quizQuestion.answer.toInt()==2){
                        holder.tvOption2.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }else if (quizQuestion.answer.toInt()==3){
                        holder.tvOption3.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }else{
                        holder.tvOption4.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }
                }
            }

        }
        holder.tvOption2.setOnClickListener {
            if (!Constant.flag){
                val wrongOptionSelected = correctOption(2, holder.tvOption2, quizQuestion)
                if (wrongOptionSelected){
                    if (quizQuestion.answer.toInt()==2){
                        holder.tvOption2.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }else if (quizQuestion.answer.toInt()==3){
                        holder.tvOption3.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }else if (quizQuestion.answer.toInt()==4){
                        holder.tvOption4.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }else{
                        holder.tvOption1.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }
                }
            }
        }
        holder.tvOption3.setOnClickListener {
            if (!Constant.flag){
                val wrongOptionSelected = correctOption(3, holder.tvOption3, quizQuestion)
                if (wrongOptionSelected){
                    if (quizQuestion.answer.toInt()==3){
                        holder.tvOption3.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }else if (quizQuestion.answer.toInt()==2){
                        holder.tvOption2.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }else if (quizQuestion.answer.toInt()==1){
                        holder.tvOption1.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }else{
                        holder.tvOption4.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }
                }
            }
        }
        holder.tvOption4.setOnClickListener {
            if (!Constant.flag){
                val wrongOptionSelected = correctOption(4, holder.tvOption4, quizQuestion)
                if (wrongOptionSelected){
                    if (quizQuestion.answer.toInt()==4){
                        holder.tvOption4.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }else if (quizQuestion.answer.toInt()==3){
                        holder.tvOption3.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }else if (quizQuestion.answer.toInt()==2){
                        holder.tvOption2.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }else{
                        holder.tvOption4.background = quizActivity.resources.getDrawable(R.drawable.correct_option_design)
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return quizQuestions.size
    }

}