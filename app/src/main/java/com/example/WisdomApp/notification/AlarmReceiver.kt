package com.example.WisdomApp.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.WisdomApp.R

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.v(R.string.log_tag.toString(), "in onReceive for ")
        Toast.makeText(context, context.getText(R.string.wisdom_push), Toast.LENGTH_SHORT).show()
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(
            context.getText(R.string.nitzan).toString(),
            context
        )
    }
}