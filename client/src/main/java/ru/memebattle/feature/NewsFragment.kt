package ru.memebattle.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import org.koin.android.ext.android.get
import ru.memebattle.ARGS_POST
import ru.memebattle.R
import ru.memebattle.common.dto.PostResponseDto
import ru.memebattle.core.BaseFragment
import ru.memebattle.core.api.NewsApi

class NewsFragment : BaseFragment() {

    val newsApi: NewsApi = get()

    private val onItemClickListener: (PostResponseDto) -> Unit = { post ->
        Navigation.findNavController(requireActivity(), R.id.host_global).navigate(
            R.id.action_mainFragment_to_postFragment,
            bundleOf(ARGS_POST to post)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}