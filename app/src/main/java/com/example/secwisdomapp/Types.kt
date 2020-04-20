package com.example.secwisdomapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.secwisdomapp.data.Question
import com.example.secwisdomapp.data.QuestionType

import kotlinx.android.synthetic.main.activity_types.*
import kotlinx.android.synthetic.main.content_types.*


class Types : AppCompatActivity() {
    private lateinit var currenctType: QuestionType

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_types)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            moveToQuestionDetails()
        }

        inflateQuestionTypes()
    }

    private fun inflateQuestionTypes() {
        enumValues<QuestionType>().forEach { type ->
            val btn = RadioButton(this)
            btn.text = type.description
            btn.id = type.ordinal

            btn.setOnClickListener { this.currenctType = type }

            val params = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
            )

            QuestionTypes.addView(btn, params)
        }
    }

    private fun moveToQuestionDetails() {
        if (this::currenctType.isInitialized) {
            val questionDetailsActivity = Intent(this, QuestionDetails::class.java)
            questionDetailsActivity.putExtra("type", this.currenctType.description)
            startActivityForResult(questionDetailsActivity, newQuestionActivityRequestCode)
//            startActivity(questionDetailsActivity)
        } else {
            val toast = Toast.makeText(applicationContext, "Fill question type", Toast.LENGTH_LONG)
            toast.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        setResult(resultCode, data)
        finish()
    }
}
