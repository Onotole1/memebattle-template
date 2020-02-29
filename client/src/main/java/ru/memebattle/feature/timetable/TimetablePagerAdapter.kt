package ru.memebattle.feature.timetable

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TimetablePagerAdapter(fragment: Fragment, private val timetableSize: Int) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = timetableSize

    override fun createFragment(position: Int): Fragment = TimetableItemFragment()
}