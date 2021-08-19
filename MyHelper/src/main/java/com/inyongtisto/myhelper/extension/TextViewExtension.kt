package com.inyongtisto.myhelper.extension

import android.graphics.Paint
import android.widget.TextView

fun TextView.coret() {
    this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}