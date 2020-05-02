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

    @ColumnInfo(name = "question_type")
    val type: String,

    @ColumnInfo(name = "question")
    var question: String,

    @ColumnInfo(name = "answer")
    var answer: String,

    // Save time in milliseconds
    @ColumnInfo(name = "interval")
    var interval: Long,

    @ColumnInfo(name = "interval_type")
    var intervalType: String

) : Parcelable
