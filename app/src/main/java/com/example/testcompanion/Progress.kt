package com.example.testcompanion
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress", primaryKeys = ["category", "subcategory", "section"])
data class Progress(
    val category: String,
    val subcategory: String,
    val section: String,
    val questionsAttempted: Int
)
