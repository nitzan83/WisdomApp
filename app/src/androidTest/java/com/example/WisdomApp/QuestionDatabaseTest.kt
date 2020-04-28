package com.example.WisdomApp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.WisdomApp.data.Question
import com.example.WisdomApp.data.QuestionDatabase
import com.example.WisdomApp.data.QuestionDao
import com.example.WisdomApp.data.QuestionType

import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {
    private lateinit var questionDao: QuestionDao
    private lateinit var db: QuestionDatabase
    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        this.db = Room.inMemoryDatabaseBuilder(context, QuestionDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        this.questionDao = this.db.questionDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        this.db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetQuestion() {
        val newQuestion = Question(type = QuestionType.YES_NO.description, question = "Hello?", answer = "Yes")
        this.questionDao.insert(newQuestion)
        val firstQuestion = this.questionDao.get(1)
        assertEquals(firstQuestion?.type, QuestionType.YES_NO.description)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndDelete() {
        val newQuestion = Question(type = QuestionType.YES_NO.description, question = "Hello?", answer = "Yes")
        this.questionDao.insert(newQuestion)

        this.questionDao.removeById(newQuestion.questionId)

        val firstQuestion = this.questionDao.get(newQuestion.questionId)

        assertEquals(firstQuestion, null)
    }

}
