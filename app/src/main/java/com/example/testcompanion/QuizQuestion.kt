package com.example.testcompanion

data class QuizQuestion(
    val question: String = "",
    val options: List<String> = emptyList(),
    val answer: String = ""
)
