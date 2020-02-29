package ru.memebattle.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.get
import ru.memebattle.R
import ru.memebattle.core.BaseFragment
import ru.memebattle.core.api.ProfileApi
import ru.memebattle.core.utils.toast

class ProfileFragment : BaseFragment() {

    private val profileApi: ProfileApi = get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.VISIBLE

        profileApi.getProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                username.text = it.username
                rate.text = it.average.toString()
                average.text = it.average.toString()

                progressBar.visibility = View.GONE
            }, {
                toast(it.localizedMessage)

                progressBar.visibility = View.GONE
            })
            .addTo(compositeDisposable)
    }
}