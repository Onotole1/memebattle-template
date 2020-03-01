package ru.memebattle.feature.timetable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_lesson.view.*
import ru.memebattle.R
import ru.memebattle.common.dto.schdule.LessonDto
import java.text.SimpleDateFormat
import java.util.*

class LessonsAdapter(
    private val lessons: List<LessonDto>
) : RecyclerView.Adapter<LessonsAdapter.LessonViewHolder>() {

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lesson, parent, false)

        return LessonViewHolder(itemView)
    }

    override fun getItemCount(): Int = lessons.size

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = lessons[position]

        holder.itemView.apply {
            subjectName.text = lesson.name
            classroom.text = lesson.classRoom
            teacher.text = lesson.teacherName
            val timeStart = SimpleDateFormat("HH:mm").format(Date(lesson.timeStart))
            val timeEnd = SimpleDateFormat("HH:mm").format(Date(lesson.timeEnd))
            time.text = "$timeStart - $timeEnd"
        }
    }

}