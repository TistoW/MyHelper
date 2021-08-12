package com.inyongtisto.myhelper.extension

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

fun SwipeRefreshLayout.setDefaultColor() {
    this.setColorSchemeColors(
        context.getColor(android.R.color.holo_green_light),
        context.getColor(android.R.color.holo_orange_light),
        context.getColor(android.R.color.holo_red_light)
    )
}

fun View.toSquare() {
    val observer = this.viewTreeObserver
    observer.addOnGlobalLayoutListener {
        this.viewTreeObserver
//            val a = view.measuredHeight
        val b = this.measuredWidth
        this.layoutParams.height = b
        this.requestLayout()
    }
}

