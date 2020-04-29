package com.example.WisdomApp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class QuestionRepository(private val questionDao: QuestionDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allQuestions: LiveData<List<Question>> = questionDao.getAllQuestions()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Question) : Long {
        return questionDao.insert(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun removeById(id: Long) {
        questionDao.removeById(id)
    }

}