package ru.memebattle.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.get
import ru.memebattle.R
import ru.memebattle.core.api.ProfileApi
import ru.memebattle.core.utils.toast

class ProfileFragment : Fragment() {

    val profileApi: ProfileApi = get()

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

        profileApi.getProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                username.text = it.username

                progressBar.visibility = View.GONE
            }, {
                toast(it.localizedMessage)

                progressBar.visibility = View.GONE
            })
    }
}