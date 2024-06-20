package com.example.testcompanion
import androidx.room.Entity

@Entity(tableName = "progress", primaryKeys = ["category", "subcategory", "section"])
data class Progress(
    val category: String,
    val subcategory: String,
    val section: String,
    val questionsAttempted: Int,
    val selectedOptions: ArrayList<Int>
)
