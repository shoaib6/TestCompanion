package com.example.testcompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onBackPressed() {
        super.onBackPressed()
        Constant.universalQuiz.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.universalQuiz.clear()
    }
}