package com.inyongtisto.myhelper

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}