package com.inyongtisto.myhelper.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


fun <T> Context.intentActivity(activity: Class<T>, value: String, name: String = "extra") {
    val i = Intent(applicationContext, activity)
    i.putExtra(name, value)
    startActivity(i)
}

fun <T> Context.intentActivity(activity: Class<T>) {
    val i = Intent(applicationContext, activity)
    startActivity(i)
}

fun <T> Context.pushActivity(activity: Class<T>) {
    val i = Intent(applicationContext, activity)
    i.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(i)
}


fun Activity.getStringExtra(name: String = "extra"): String? {
    return intent.getStringExtra(name)
}