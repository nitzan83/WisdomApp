package com.example.WisdomApp

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.WisdomApp.data.Question
import com.example.WisdomApp.data.QuestionViewModel
import kotlinx.android.synthetic.main.activity_main.*


val newQuestionActivityRequestCode = 1

class MainActivity : AppCompatActivity() {

    private lateinit var questionViewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        var lambda = {id: Long -> questionViewModel.remove_by_id(id) }
        val adapter = QuestionListAdapter(this, lambda)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        questionViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

        questionViewModel.allQuestions.observe(this, Observer { questions ->
            // Update the cached copy of the words in the adapter.
            questions?.let { adapter.setQuestions(it) }
        })

        val questionTypeActivity = Intent(this, Types::class.java)
        fab.setOnClickListener { view ->
            startActivityForResult(questionTypeActivity, newQuestionActivityRequestCode)
        }

        createChannel(
            getString(R.string.wisdom_notification_channel_id),
            getString(R.string.wisdom_notification_channel_name)
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ( resultCode == Activity.RESULT_OK)
        {
            val question: Question? = data?.getParcelableExtra(QuestionDetails.REPLY_NEW_QUESTION)
            if (question != null) {
                questionViewModel.insert(question)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Did not work!!!!!!!!!!",
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_HIGH
            )// TODO: Step 2.6 disable badges for this channel
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.wisdom_notification_channel_description)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(notificationChannel)

        }
        // TODO: Step 1.6 END create a channel
    }


}
