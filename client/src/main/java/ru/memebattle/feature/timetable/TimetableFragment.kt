package ru.memebattle.feature.timetable


import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import ru.memebattle.core.utils.snack
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class TimetableFragment : BaseFragment() {

    private val timetableApi: TimetableApi = get()
    private val prefs: SharedPreferences = get()
    private var timetableList = arrayListOf<ScheduleDayDto>()

    private val testLessonsList = arrayListOf<LessonDto>().apply {
        for (i in 1..8) add(
            LessonDto(
                i.toLong(),
                "xuy",
                "хуйло",
                i.toLong(),
                "xuy",
                i.toLong(),
                i.toLong()
            )
        )
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
        todayButton.setOnClickListener {
            scrollToToday()
        }
        refreshButton.setOnClickListener {
            request()
        }
        request()
    }

    private fun request() {
        progressBar.visibility = View.VISIBLE
        timetableApi.getTimetableList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                timetableList = ArrayList(it)

                prefs.putTimetableList(timetableList)
                val pagerAdapter =
                    TimetablePagerAdapter(this, timetableListTest.size, timetableList)
                viewPager.adapter = pagerAdapter
                progressBar.visibility = View.GONE
                scrollToToday()
                todayButton.isEnabled = true
            }, { error ->
                snack(error.localizedMessage.toString())
                timetableList = timetableListTest

                // МОКИ
                prefs.putTimetableList(timetableList)
                val pagerAdapter =
                    TimetablePagerAdapter(this, timetableListTest.size, timetableList)
                viewPager.adapter = pagerAdapter
                progressBar.visibility = View.GONE
                scrollToToday()
                todayButton.isEnabled = true

            }).addTo(compositeDisposable)
    }

    private fun scrollToToday() {
        timetableList.forEach {
            if (Date(it.date).day == Date().day) {
                viewPager.setCurrentItem(timetableList.indexOf(it), true)
                return
            }
        }
    }
}
