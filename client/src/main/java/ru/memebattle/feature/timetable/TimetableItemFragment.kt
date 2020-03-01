package ru.memebattle.feature.timetable


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.fragment_timetable_item.*

import ru.memebattle.R
import ru.memebattle.common.dto.schdule.LessonDto
import ru.memebattle.common.dto.schdule.ScheduleDayDto
import java.text.DateFormat
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class TimetableItemFragment(private val dayTimetable: ScheduleDayDto) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timetable_item, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date = Date(dayTimetable.date)
        val month = when (date.month) {
            0 -> "Января"
            1 -> "Февраля"
            2 -> "Марта"
            3 -> "Апреля"
            4 -> "Мая"
            5 -> "Июня"
            6 -> "Июля"
            7 -> "Августа"
            8 -> "Сентября"
            9 -> "Октября"
            10 -> "Ноября"
            11 -> "Декабря"
            else -> ""
        }
        val week = when (date.day) {
            0 -> "Понедельник"
            1 -> "Вторник"
            2 -> "Среда"
            3 -> "Четверг"
            4 -> "Пятница"
            5 -> "Суббта"
            6 -> "Воскресенье"
            else -> ""
        }
        dateTextView.text = "${date.date} $month\n\n$week"

        recyclerViewLessons?.adapter = LessonsAdapter(dayTimetable.lessons)
    }

}
