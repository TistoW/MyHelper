package com.inyongtisto.myhelper.extension

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.internal.Primitives
import java.lang.reflect.Type

fun <T> T.toJson(): String {
    return Gson().toJson(this)
}

fun <T> String?.toModel(classOfT: Class<T>): T? {
    if (this == null) return null
    val obj = Gson().fromJson<Any>(this, classOfT as Type)
    return Primitives.wrap(classOfT).cast(obj)!!
}

