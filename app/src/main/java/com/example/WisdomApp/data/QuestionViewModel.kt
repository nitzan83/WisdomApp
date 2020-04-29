package com.example.WisdomApp.data

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.WisdomApp.ALARM_RECEIVER_ACTION
import com.example.WisdomApp.notification.AlarmReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository: QuestionRepository
    // Using LiveData and caching allQuestions has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allQuestions: LiveData<List<Question>>
    private lateinit var alarmManager: AlarmManager


    init {
        val questionDao = QuestionDatabase.getInstance(application, viewModelScope).questionDao()
        repository = QuestionRepository(questionDao)
        allQuestions = repository.allQuestions

        alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun getAlarmIntent(): Intent {
        val intent = Intent(getApplication(), AlarmReceiver::class.java)
        intent.action = ALARM_RECEIVER_ACTION
        return intent
    }


    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(question: Question) = viewModelScope.launch(Dispatchers.IO) {
        Log.v("Hey", "Inserting question")
        val id = repository.insert(question)
        Log.v("Hey", "New question id ${question.questionId}")

        val pendingIntent = PendingIntent.getBroadcast(
                    getApplication(),
                    id.toInt(), getAlarmIntent(), PendingIntent.FLAG_UPDATE_CURRENT)

                //TODO: change to question interval
                val timeInterval = 5 * 1_000L
                val alarmTime = System.currentTimeMillis() + 5_000L

                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    alarmTime,
                    timeInterval,
                    pendingIntent
                )
    }

    fun removeById(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        Log.v("Hey", "Removeing question id $id")
        PendingIntent.getBroadcast(getApplication(), id.toInt(), getAlarmIntent(), PendingIntent.FLAG_UPDATE_CURRENT).cancel()
        repository.removeById(id)
    }
}