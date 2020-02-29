package ru.memebattle.feature.timetable


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_timetable_item.*

import ru.memebattle.R
import ru.memebattle.common.dto.schdule.LessonDto
import ru.memebattle.common.dto.schdule.ScheduleDayDto

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewLessons?.adapter = LessonsAdapter(dayTimetable.lessons)
    }

}
