package com.example.WisdomApp.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.WisdomApp.R
import com.example.WisdomApp.data.Question


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.v(R.string.log_tag.toString(), "in onReceive for ")
        Toast.makeText(context, context.getText(R.string.wisdom_push), Toast.LENGTH_SHORT).show()
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        val todoBundle = intent.getBundleExtra(R.string.repatingQuestionBundle.toString())

        if (todoBundle != null) {
            val question = todoBundle.getParcelable<Question>(R.string.notification_question.toString())
            if (question != null) {
                notificationManager.sendNotification(question.question, context)
            }
        }
    }
}