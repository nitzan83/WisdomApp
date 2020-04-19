package com.example.secwisdomapp

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.secwisdomapp.data.AnswerYesNo
import com.example.secwisdomapp.data.Question
import com.example.secwisdomapp.data.QuestionType
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_question_details.*
import kotlinx.android.synthetic.main.content_question_details.*
import kotlinx.android.synthetic.main.content_types.*


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
        }
        val type = intent.getStringExtra("type")
        TypeText.text = type

        // later all the answer part can be in a fragment and than will have a different way foe
        // each question type
        inflateQuestionAnswers()
    }

    private fun addQuestion()
    {
        // Create question from type, question and answer
        // type from intent

        // question from QuestionText

        // Answer from this.currentAnswer
        val type: QuestionType = QuestionType.valueOf(TypeText.text as String)
        val question: String = QuestionText.text.toString()
        val answer: String = this.currenctAnswer.toString()

        val newQuestion = Question(type, question, answer)

        // add to data base

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


}
