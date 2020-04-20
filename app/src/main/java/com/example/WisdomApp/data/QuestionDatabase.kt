package com.example.WisdomApp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Question::class], version = 1)
abstract class QuestionDatabase : RoomDatabase() {
//    abstract val questionDatabaseDao: QuestionDatabaseDao
    abstract fun questionDao(): QuestionDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: QuestionDatabase? = null

        fun getInstance(context: Context,
                        scope: CoroutineScope
        ): QuestionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuestionDatabase::class.java,
                    "question_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(QuestionDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class QuestionDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.questionDao())
                }
            }
        }

        fun populateDatabase(questionDao: QuestionDatabaseDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.

            // Delete all content here.
            questionDao.clear()

            // Add sample questions.
            var question = Question(type = QuestionType.YES_NO.description, question = "Hello?", answer = "Yes")
            questionDao.insert(question)

            question = Question(type = QuestionType.YES_NO.description, question = "Anyone here?", answer = "Yes")
            questionDao.insert(question)
        }
    }
}
