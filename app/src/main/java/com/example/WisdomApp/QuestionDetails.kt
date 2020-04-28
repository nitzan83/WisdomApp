package com.example.WisdomApp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.WisdomApp.data.AnswerYesNo
import com.example.WisdomApp.data.Question
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_question_details.*
import kotlinx.android.synthetic.main.content_question_details.*


class QuestionDetails : AppCompatActivity() {
    private lateinit var currenctAnswer: AnswerYesNo

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
        val type = intent.getStringExtra("type")
        TypeText.text = type

        // later all the answer part can be in a fragment and than will have a different way foe
        // each question type
        inflateQuestionAnswers()
    }

    private fun addQuestion()
    {
        val type: String = TypeText.text as String
        //TODO check these fiels are full
        val question: String = QuestionText.text.toString()
        val answer: String = this.currenctAnswer.description

        val newQuestion = Question(type=type, question=question, answer=answer)
        val replyIntent = Intent()

        replyIntent.putExtra(REPLY_NEW_QUESTION, newQuestion)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    private fun inflateQuestionAnswers() {
        enumValues<AnswerYesNo>().forEach { answer ->
            val btn = RadioButton(this)
            btn.text = answer.description
            btn.id = answer.ordinal

            btn.setOnClickListener { this.currenctAnswer = answer }

            // TODO maybe move this into a const so all radio groups can use it
            val params = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT)

            QuestionAnswers.addView(btn, params)
        }
    }

    companion object {
        const val REPLY_NEW_QUESTION = "new_question"
    }
}


