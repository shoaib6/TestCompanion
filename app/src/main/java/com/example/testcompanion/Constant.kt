package com.example.testcompanion

import android.graphics.drawable.Drawable

class Constant {
    companion object{
        var flag = false
        var Category = ""
        var Subject = ""
        var selectedOptions = ArrayList<Int>()
        var goingBack = false
        var universalIndex = 0
        var PrepareMode = true
        var QuizMode = false
        var universalQuiz = ArrayList<QuizQuestion>()
        var remainingTime = ""
        var totalQuestionsAttempted = 0
        var attempted = false
    }
}