package ru.memebattle.feature

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.get
import ru.memebattle.PREFS_TOKEN
import ru.memebattle.R
import ru.memebattle.core.utils.getString

class SplashFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs: SharedPreferences = get()

        if (prefs.getString(PREFS_TOKEN) != null) {
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_authFragment)
        }
    }
}