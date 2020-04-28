package com.example.WisdomApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.WisdomApp.data.Question
import kotlinx.coroutines.Job

class QuestionListAdapter internal constructor(
    context: Context,
    private val deleteCallback: (Long) -> Job
) : RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var questions = emptyList<Question>() // Cached copy of questions

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionItemView: TextView = itemView.findViewById(R.id.questionTextView)
        val answerItemView: TextView = itemView.findViewById(R.id.answerTextView)
        val deleteButton: Button = itemView.findViewById(R.id.delete_question_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return QuestionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val current = questions[position]

        holder.questionItemView.text = current.question
        holder.answerItemView.text = current.answer
        holder.deleteButton.setOnClickListener{ deleteCallback(current.questionId) }
    }

    internal fun setQuestions(questions: List<Question>) {
        this.questions = questions
        notifyDataSetChanged()
    }

    override fun getItemCount() = questions.size
}

