package ru.memebattle.core

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

    val compositeDisposable = CompositeDisposable()

    override fun onStop() {
        super.onStop()

        compositeDisposable.clear()
    }
}