package ru.memebattle.feature.timetable


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_timetable.*
import org.koin.android.ext.android.get

import ru.memebattle.R
import ru.memebattle.common.dto.schdule.LessonDto
import ru.memebattle.common.dto.schdule.ScheduleDayDto
import ru.memebattle.core.BaseFragment
import ru.memebattle.core.api.TimetableApi
import ru.memebattle.core.utils.putTimetableList

/**
 * A simple [Fragment] subclass.
 */
class TimetableFragment : BaseFragment() {

    private val timetableApi: TimetableApi = get()
    private val prefs: SharedPreferences = get()


    private val testLessonsList = arrayListOf<LessonDto>().apply {
        for (i in 1..8) add(LessonDto(i.toLong(), "xuy", i.toLong(), "xuy", i.toLong(), i.toLong()))
    }

    private var timetableListTest = arrayListOf<ScheduleDayDto>().apply {
        for (i in 1..10) add(ScheduleDayDto(i.toLong(), i * 1000L, testLessonsList))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timetable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timetableApi.getTimetableList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                prefs.putTimetableList(it)
                val pagerAdapter =
                    TimetablePagerAdapter(this, timetableListTest.size, timetableListTest)
                viewPager.adapter = pagerAdapter
            }, { error ->
                Snackbar.make(view, error.localizedMessage.toString(), Snackbar.LENGTH_SHORT).show()
            }).addTo(compositeDisposable)
    }
}
