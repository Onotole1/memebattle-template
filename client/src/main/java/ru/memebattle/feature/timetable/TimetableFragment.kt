package ru.memebattle.feature.timetable


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.fragment_timetable.*

import ru.memebattle.R

/**
 * A simple [Fragment] subclass.
 */
class TimetableFragment : Fragment() {

    private var timetableSize: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timetable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = TimetablePagerAdapter(this, timetableSize)
        viewPager.adapter = pagerAdapter

    }
}
