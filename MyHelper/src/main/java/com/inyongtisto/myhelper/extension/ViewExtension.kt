package com.inyongtisto.myhelper.extension

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.inyongtisto.myhelper.R

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.visible(status: Boolean) {
    if (status) this.toVisible() else this.toGone()
}

fun SwipeRefreshLayout.setDefaultColor() {
    this.setColorSchemeColors(
        context.getColor(android.R.color.holo_green_light),
        context.getColor(android.R.color.holo_orange_light),
        context.getColor(android.R.color.holo_red_light)
    )
}

fun SwipeRefreshLayout.showLoading() {
    this.isRefreshing = true
}

fun SwipeRefreshLayout.dismissLoading() {
    this.isRefreshing = false
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

fun Spinner.setOnPositionSelectedListener(
    context: Context,
    array: List<String>,
    layout: Int = R.layout.item_spinner,
    onSelected: (pos: Int) -> Unit
) {
    val adapter = ArrayAdapter<Any>(context, layout, array.toTypedArray())
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.adapter = adapter
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onSelected.invoke(position)
        }
    }
}

fun Spinner.setOnItemSelectedListener(
    context: Context,
    array: List<String>,
    layout: Int = R.layout.item_spinner,
    onSelected: (item: String) -> Unit
) {
    val adapter = ArrayAdapter<Any>(context, R.layout.item_spinner, array.toTypedArray())
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.adapter = adapter
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onSelected.invoke(array[position])
        }
    }
}
