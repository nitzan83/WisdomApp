package com.example.secwisdomapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.secwisdomapp.data.Question
import com.example.secwisdomapp.data.QuestionViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

val newQuestionActivityRequestCode = 1

class MainActivity : AppCompatActivity() {

    private lateinit var questionViewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = QuestionListAdapter(this)
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
}
