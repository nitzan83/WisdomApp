package com.example.secwisdomapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class QuestionRepository(private val questionDao: QuestionDatabaseDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allQuestions: LiveData<List<Question>> = questionDao.getAllQuestions()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Question) {
        questionDao.insert(word)
    }
}