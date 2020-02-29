package ru.memebattle.feature.timetable

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.memebattle.common.dto.schdule.ScheduleDayDto

class TimetablePagerAdapter(
    fragment: Fragment,
    private val timetableSize: Int,
    private val timetableList: List<ScheduleDayDto>
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = timetableSize

    override fun createFragment(position: Int): Fragment =
        TimetableItemFragment(timetableList[position])
}