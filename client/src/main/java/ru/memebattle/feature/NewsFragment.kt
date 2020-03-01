package ru.memebattle.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_news.*
import ru.memebattle.R
import ru.memebattle.core.BaseFragment

class NewsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        memebattle.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.host_global)
                .navigate(R.id.action_mainFragment_to_memeBattleFragment)
        }
    }
}