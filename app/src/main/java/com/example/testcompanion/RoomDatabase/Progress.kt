package com.example.testcompanion.RoomDatabase
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "progress", primaryKeys = ["category", "subcategory", "section"])
data class Progress(
    val category: String,
    val subcategory: String,
    val section: String,
    val questionsAttempted: Int,
    val selectedOptions: ArrayList<Int>,
    val timeRemaining: Long,
    val accuracy: Float
)
