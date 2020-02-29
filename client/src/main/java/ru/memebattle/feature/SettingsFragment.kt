package ru.memebattle.feature

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.get
import ru.memebattle.PREFS_ALARM
import ru.memebattle.PREFS_ALARM_HOUR
import ru.memebattle.PREFS_ALARM_MINUTE
import ru.memebattle.R
import ru.memebattle.core.utils.getTimetableList
import java.util.*

class SettingsFragment : Fragment() {

    companion object {
        private const val SET_ALARM_CODE = 100
    }

    private val sharedPreferences: SharedPreferences = get()

    private var alarmIntents = mutableListOf<Intent>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scheduleDays = sharedPreferences.getTimetableList()

        timePicker.hour = sharedPreferences.getInt(PREFS_ALARM_HOUR, 1)
        timePicker.minute = sharedPreferences.getInt(PREFS_ALARM_MINUTE, 0)
        switch1.isChecked = sharedPreferences.getBoolean(PREFS_ALARM, false)

        timePicker.setIs24HourView(true)
        timePicker.setOnTimeChangedListener { timePicker, hour, minute ->
            switch1.isChecked = false
            sharedPreferences.edit().putInt(PREFS_ALARM_HOUR, hour).apply()
            sharedPreferences.edit().putInt(PREFS_ALARM_MINUTE, hour).apply()
        }

        switch1.setOnCheckedChangeListener { button, checked ->
            if (checked) {
                sharedPreferences.edit().putBoolean(PREFS_ALARM, true).apply()
                alarmIntents = mutableListOf()
                scheduleDays.forEach {
                    val date = Date(it.date - timePicker.hour * 3600000 - timePicker.minute * 60000)
                    val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                    intent.putExtra(
                        AlarmClock.EXTRA_MESSAGE,
                        "Доброе утро! Пора на ${it.lessons[0].id}"
                    )
                    intent.putExtra(AlarmClock.EXTRA_HOUR, date.hours)
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, date.minutes)
                    intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true)
                    alarmIntents.add(intent)
                }
                startActivityForResult(alarmIntents[0], SET_ALARM_CODE)
            } else {
                sharedPreferences.edit().putBoolean(PREFS_ALARM, false).apply()
            }
        }
    }
}