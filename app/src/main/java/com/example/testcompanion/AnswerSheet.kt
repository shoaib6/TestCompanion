package com.example.testcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testcompanion.databinding.ActivityAnswerSheetBinding

class AnswerSheet : AppCompatActivity() {
    private lateinit var binding: ActivityAnswerSheetBinding
    private lateinit var answerSheetAdapter: AnswerSheetAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_sheet)
        binding = ActivityAnswerSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = GridLayoutManager(this,5)
        binding.answerSheetRecyclerview.addItemDecoration(GridSpacingItemDecoration(this,5,15,true))
        answerSheetAdapter = AnswerSheetAdapter(this)
        binding.answerSheetRecyclerview.layoutManager = layoutManager
        binding.answerSheetRecyclerview.adapter = answerSheetAdapter
        binding.remainingTimetextView.text = Constant.remainingTime


    }

    fun checkAnswers(position: Int) {
        Constant.checkingQuestion = position
        Constant.QuestionNo = position
        val intent = Intent(this,QuizActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Constant.universalQuiz.clear()
        Constant.totalQuestionsAttempted = 0
        Constant.isCheckingAnswers = false
        Constant.selectedOptions.clear()
        val intent = Intent(this, SectionsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.universalQuiz.clear()
    }
}