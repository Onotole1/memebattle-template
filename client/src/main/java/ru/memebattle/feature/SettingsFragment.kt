package ru.memebattle.feature

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.*
import ru.memebattle.R


class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timePicker.setIs24HourView(true)
        timePicker.setOnTimeChangedListener { timePicker, hour, minute ->

        }
        switch1.setOnCheckedChangeListener { button, checked ->
            if (checked) {
                for (i in 0..10) {
                    val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE, "На пары $i")
                    intent.putExtra(AlarmClock.EXTRA_HOUR, i)
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, i)
                    intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            } else {

            }
        }
    }
}