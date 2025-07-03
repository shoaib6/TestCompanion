package com.example.testcompanion.RoomDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: Progress)

    @Query("SELECT * FROM progress WHERE category = :category AND subcategory = :subcategory AND section = :section LIMIT 1")
    suspend fun getProgress(category: String, subcategory: String, section: String): Progress?

    @Update
    suspend fun updateQuizProgress(progress: Progress)

    @Query("SELECT section, accuracy FROM progress WHERE category = :category AND subcategory = :subcategory")
    suspend fun getSectionAccuracies(category: String, subcategory: String): List<SectionAccuracy>

}