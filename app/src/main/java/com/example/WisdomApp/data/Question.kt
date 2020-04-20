package com.example.WisdomApp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "questions_table")
data class Question(
    @PrimaryKey(autoGenerate = true)
    var questionId: Long = 0L,

    // TODO change this back to QuestinoType
    @ColumnInfo(name = "question_type")
    val type: String,

    @ColumnInfo(name = "question")
    var question: String,

    @ColumnInfo(name = "answer")
    var answer: String
) : Parcelable
