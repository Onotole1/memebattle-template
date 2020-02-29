package ru.memebattle.feature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_subject.view.*
import ru.memebattle.R
import ru.memebattle.common.dto.user.SemesterSubjectDto

class SubjectsAdapter(
    private val subjects: List<SemesterSubjectDto>
) : RecyclerView.Adapter<SubjectsAdapter.SemesterViewHolder>() {

    class SemesterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SemesterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subject, parent, false)

        return SemesterViewHolder(itemView)
    }

    override fun getItemCount(): Int = subjects.size

    override fun onBindViewHolder(holder: SemesterViewHolder, position: Int) {
        val subject = subjects[position]

        holder.itemView.apply {
            subjectName.text = subject.id.toString()
            average.text = subject.average.toString()
            rate.text = subject.position.toString()
        }
    }

}