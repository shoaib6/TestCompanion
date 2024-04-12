package com.example.testcompanion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnswerSheetAdapter(private val answerSheet: AnswerSheet) : RecyclerView.Adapter<AnswerSheetAdapter.ViewHolder>() {
   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       val answerBox: LinearLayout = itemView.findViewById(R.id.answerBox)
       val questionNo: TextView = itemView.findViewById(R.id.questionNo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : AnswerSheetAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_answersheet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnswerSheetAdapter.ViewHolder, position: Int) {
        holder.questionNo.text = "Q ${position+1}"
        val quizQuestion = Constant.universalQuiz[position]
        if (Constant.selectedOptions[position]==quizQuestion.answer.toInt()){
            holder.answerBox.background = answerSheet.resources.getDrawable(R.drawable.correct_result_box_design)
        }else{
            holder.answerBox.background = answerSheet.resources.getDrawable(R.drawable.wrong_result_box_design)
        }
        //
        holder.answerBox.setOnClickListener {
            Constant.isCheckingAnswers = true
            answerSheet.checkAnswers(position)
        }
        //
    }

    override fun getItemCount(): Int {
        return Constant.totalQuestionsAttempted
    }
}