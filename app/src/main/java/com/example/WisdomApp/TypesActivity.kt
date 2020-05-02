package com.example.WisdomApp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.WisdomApp.data.QuestionType
import com.example.WisdomApp.utils.radioGroupLayoutParams

import kotlinx.android.synthetic.main.activity_types.*
import kotlinx.android.synthetic.main.content_types.*


class TypesActivity : AppCompatActivity() {
    private lateinit var currenctType: QuestionType

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_types)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {moveToQuestionDetails()}

        inflateQuestionTypes()
    }

    private fun inflateQuestionTypes() {
        enumValues<QuestionType>().forEach { type ->
            val btn = RadioButton(this)
            btn.text = type.description
            btn.id = type.ordinal

            btn.setOnClickListener { this.currenctType = type }

            QuestionTypes.addView(btn, radioGroupLayoutParams)
        }
    }

    private fun moveToQuestionDetails() {
        if (this::currenctType.isInitialized) {
            val questionDetailsActivity = Intent(this, DetailsActivity::class.java)
            questionDetailsActivity.putExtra("type", this.currenctType.description)
            startActivityForResult(questionDetailsActivity, newQuestionActivityRequestCode)
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
