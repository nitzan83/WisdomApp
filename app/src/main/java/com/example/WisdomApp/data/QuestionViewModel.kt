package com.example.WisdomApp.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Logger

class QuestionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: QuestionRepository
    // Using LiveData and caching what getAlphabetizedQuestions returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allQuestions: LiveData<List<Question>>

    init {
        val questionDao = QuestionDatabase.getInstance(application, viewModelScope).questionDao()
        repository = QuestionRepository(questionDao)
        allQuestions = repository.allQuestions
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(question: Question) = viewModelScope.launch(Dispatchers.IO) {
        Log.v("Hey", "Inserting question id ${question.questionId}")
        repository.insert(question)
    }

    fun remove_by_id(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        Log.v("Hey", "Removeing question id $id")
        repository.remove_by_id(id)
    }
}