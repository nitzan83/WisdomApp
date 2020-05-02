package com.example.WisdomApp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.WisdomApp.data.AnswerYesNo
import com.example.WisdomApp.data.Question
import com.example.WisdomApp.utils.intervalInSeconds
import com.example.WisdomApp.utils.intervalOptionToSeconds
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_question_details.*
import kotlinx.android.synthetic.main.content_question_details.*

val radioGroupParams = RadioGroup.LayoutParams(
    RadioGroup.LayoutParams.WRAP_CONTENT,
    RadioGroup.LayoutParams.WRAP_CONTENT
)

class QuestionDetails : AppCompatActivity() {
    private lateinit var currenctAnswer: AnswerYesNo
    private lateinit var timeOption: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_details)
        setSupportActionBar(toolbar)
        val intent = intent
        fab.setOnClickListener { view ->
            Snackbar.make(
                view,
                "In details ", Snackbar.LENGTH_LONG
            )
                .setAction("Action", null).show()
            addQuestion()
        }
        // TODO: change extra to const string in xml
        val type = intent.getStringExtra("type")
        TypeText.text = type

        //TODO: answer part can be in a fragment and than will have a different way for each question type
        inflateQuestionAnswers()
        inflateTimeOptions()
    }

    private fun addQuestion() {
        val type: String = TypeText.text as String
        //TODO: add checks if these fields are full
        val question: String = QuestionText.text.toString()
        val answer: String = this.currenctAnswer.description

        val newQuestion =
            intervalInSeconds(this.timeOption, TimeText.text.toString().toLong())?.let {
                Question(
                    type = type,
                    question = question,
                    answer = answer,
                    interval = it,
                    intervalType = this.timeOption
                )
            }

        val replyIntent = Intent()

        replyIntent.putExtra(REPLY_NEW_QUESTION, newQuestion)
        setResult(Activity.RESULT_OK, replyIntent)

        finish()
    }

    private fun inflateTimeOptions() {
        intervalOptionToSeconds.keys.forEach { option ->
            val btn = RadioButton(this)
            btn.text = option
            btn.setOnClickListener { this.timeOption = option }
            TimeOptions.addView(btn, radioGroupParams)
        }
    }

    private fun inflateQuestionAnswers() {
        enumValues<AnswerYesNo>().forEach { answer ->
            val btn = RadioButton(this)
            btn.text = answer.description
            btn.id = answer.ordinal

            btn.setOnClickListener { this.currenctAnswer = answer }

            QuestionAnswers.addView(btn, radioGroupParams)
        }
    }

    companion object {
        const val REPLY_NEW_QUESTION = "new_question"
    }
}


