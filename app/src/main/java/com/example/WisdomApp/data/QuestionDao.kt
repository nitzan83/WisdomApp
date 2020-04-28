package com.example.WisdomApp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(question: Question)

    @Update
    fun update(question: Question)

    @Query("SELECT * from questions_table WHERE questionId = :key")
    fun get(key: Long): Question?

    @Query("DELETE FROM questions_table")
    fun clear()

    @Query("SELECT * FROM questions_table ORDER BY questionId ASC")
    fun getAllQuestions(): LiveData<List<Question>>

    @Query("DELETE FROM questions_table WHERE questionId = :key")
    fun removeById(key: Long)
}