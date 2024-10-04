package com.example.testcompanion.ConstantVariables

import com.example.testcompanion.QuizQuestion
import com.example.testcompanion.RoomDatabase.AppDatabase

class Constant {
    companion object{
        var flag = false
        var Category = ""
        var Subject = ""
        var SectionsName = ""
        var selectedOptions = ArrayList<Int>()
        var goingBack = false
        var universalIndex = 0
        var PrepareMode = true
        var QuizMode = false
        var universalQuiz = ArrayList<QuizQuestion>()
        var remainingTime = "00:00"
        var totalQuestionsAttempted = 0
        var attempted = false
        var checkingQuestion = 0
        var isCheckingAnswers = false
        var QuestionNo = 0
        lateinit var appDatabase: AppDatabase

    }
}