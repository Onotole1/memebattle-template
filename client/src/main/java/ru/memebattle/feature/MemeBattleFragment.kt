package ru.memebattle.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_memebattle.*
import org.koin.android.ext.android.get
import ru.memebattle.R
import ru.memebattle.common.dto.game.MemeRequest
import ru.memebattle.common.dto.game.MemeResponse
import ru.memebattle.core.BaseFragment
import ru.memebattle.core.api.GameApi
import ru.memebattle.core.utils.log
import ru.memebattle.core.utils.toast
import java.util.concurrent.TimeUnit

class MemeBattleFragment : BaseFragment() {

    private val gameApi: GameApi = get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_memebattle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image1.setOnClickListener {
            if (like1.visibility == View.GONE && like2.visibility == View.GONE) {
                like1.visibility = View.VISIBLE
            }
            sendLike(0)
        }

        image2.setOnClickListener {
            if (like1.visibility == View.GONE && like2.visibility == View.GONE) {
                like2.visibility = View.VISIBLE
            }
            sendLike(1)
        }

        gameApi.getState()
            .repeatUntil {
                false
            }
            .delay(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                log("get state res $it")
                processState(it)
            }, {
                log("get state err $it")
                toast(it.localizedMessage)
            })
            .addTo(compositeDisposable)
    }

    private fun sendLike(num: Int) {
        gameApi.sendLike(MemeRequest(num))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                log("send like res $it")
                processState(it)
            }, {
                log("send like err $it")
                toast(it.localizedMessage)
            })
            .addTo(compositeDisposable)
    }

    private fun processState(memeResponse: MemeResponse) {
        when (memeResponse.state) {
            "start" -> {
                like1.visibility = View.GONE
                like2.visibility = View.GONE
                result1.visibility = View.GONE
                result2.visibility = View.GONE
            }
            "memes" -> {
                result1.visibility = View.GONE
                result2.visibility = View.GONE
                Glide.with(requireActivity())
                    .load(memeResponse.memes[0])
                    .into(image1)
                Glide.with(requireActivity())
                    .load(memeResponse.memes[1])
                    .into(image2)
            }
            "result" -> {
                like1.visibility = View.GONE
                like2.visibility = View.GONE
                result1.visibility = View.VISIBLE
                result2.visibility = View.VISIBLE
                res1.text = "${memeResponse.likes[0]} likes"
                res2.text = "${memeResponse.likes[1]} likes"
            }
        }
    }
}