package ru.memebattle.core.utils

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Any.log(text: String) {
    Log.i("lol", text)
}

fun Activity.snack(text: String) {
    val view = this.window.decorView.rootView
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.snack(text: String) {
    val view = this.activity!!.window.decorView.rootView
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}

fun Activity.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(text: String) {
    Toast.makeText(this.activity, text, Toast.LENGTH_SHORT).show()
}