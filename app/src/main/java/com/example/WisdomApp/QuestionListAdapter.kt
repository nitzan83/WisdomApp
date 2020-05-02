package com.example.WisdomApp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.WisdomApp.data.Question
import com.example.WisdomApp.utils.intervalInTimeOption
import kotlinx.coroutines.Job

class QuestionListAdapter internal constructor(
    context: Context,
    private val deleteCallback: (Long) -> Job
) : RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var questions = emptyList<Question>() // Cached copy of questions

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // A class that contains all view elements for one item 
        val questionItemView: TextView = itemView.findViewById(R.id.questionTextView)
        val answerItemView: TextView = itemView.findViewById(R.id.answerTextView)
        val intervalItemView: TextView = itemView.findViewById(R.id.intervalTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteQuestionButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return QuestionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        // Binds the view holder to a position 
        val question = questions[position]
        holder.questionItemView.text = question.question
        holder.answerItemView.text = question.answer
        holder.intervalItemView.text = "${intervalInTimeOption(question.intervalType, question.interval).toString()} ${question.intervalType}"


        holder.deleteButton.setOnClickListener { deleteCallback(question.questionId) }
    }

    internal fun setQuestions(questions: List<Question>) {
        this.questions = questions
        notifyDataSetChanged()
    }

    override fun getItemCount() = questions.size
}

