package ru.memebattle.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.ext.android.get
import ru.memebattle.ARGS_POST
import ru.memebattle.R
import ru.memebattle.common.dto.PostResponseDto
import ru.memebattle.core.BaseFragment
import ru.memebattle.core.api.NewsApi
import ru.memebattle.core.utils.toast

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

        progressBar.visibility = View.VISIBLE

        newsApi.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                recyclerViewNews?.adapter = NewsAdapter(it, onItemClickListener)

                progressBar?.visibility = View.GONE
            }, {
                toast(it.localizedMessage)

                progressBar.visibility = View.GONE
            })
            .addTo(compositeDisposable)
    }
}