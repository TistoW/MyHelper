package com.inyongtisto.myhelper.extension

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.NestedScrollView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.inyongtisto.myhelper.R
import com.squareup.picasso.Picasso

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

fun Activity.setStatusBarBackgroudColor(color: Int) {
    val window: Window = window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = getColor(color)
}

fun AppCompatImageView.setTintColor(context: Context, color: Int) {
    setColorFilter(
        ContextCompat.getColor(context, color),
        android.graphics.PorterDuff.Mode.MULTIPLY
    )
}

fun AppCompatImageView.setTintColor(color: Int) {
    ImageViewCompat.setImageTintList(this, ContextCompat.getColorStateList(context, R.color.colorPrimary))
}

fun Activity.lightStatusBar() {
    val decor = this.window.decorView
    decor.systemUiVisibility = decor.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() //set status text  light
}

fun Activity.blackStatusBar() {
    val decor = this.window.decorView
    decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

fun Activity.setLightStatusBar() {
    lightStatusBar()
}

fun Activity.setBlackStatusBar() {
    blackStatusBar()
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

fun View.setVisibelityWithAnimation(
    views: ViewGroup,
    transition: Transition = Slide(Gravity.START)
) {
    // example
    // var transition: Transition = Fade()
    // var transition: Transition = Slide(Gravity.BOTTOM)
    val show: Boolean = this.visibility != View.VISIBLE

    transition.duration = 400
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(views, transition)
    this.visibility = if (show) View.VISIBLE else View.GONE
}

fun NestedScrollView.scrollToBottom() {
    post {
        fullScroll(ScrollView.FOCUS_DOWN)
    }
}

fun NestedScrollView.scrollToTop() {
    post {
        fullScroll(ScrollView.FOCUS_DOWN)
    }
}

fun ScrollView.scrollToBottom() {
    post {
        fullScroll(ScrollView.FOCUS_DOWN)
    }
}

fun ScrollView.scrollToTop() {
    post {
        fullScroll(ScrollView.FOCUS_DOWN)
    }
}

fun ImageView.setImagePicasso(url: String, error: Int = R.color.gray5, onError: ((String) -> Unit)? = null) {
    val picasso = Picasso.Builder(context)
            .listener { _, _, exception ->
                onError?.invoke(exception.message ?: "Error")
                logs("Error:" + exception.message)
            }.build()
    picasso.load(url)
            .error(error)
            .into(this)
}

fun MaterialButton.setStrokeColor(color: Int) {
    strokeColor = ColorStateList.valueOf(context.getColor(color))
}
