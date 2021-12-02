package com.inyongtisto.myhelper.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.internal.Primitives
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.lang.Exception
import java.lang.reflect.Type

fun <T> T.toJson(): String {
    return Gson().toJson(this)
}

fun <T> String?.toModel(classOfT: Class<T>): T? {
    if (this == null) return null
    val obj = Gson().fromJson<Any>(this, classOfT as Type)
    return Primitives.wrap(classOfT).cast(obj)!!
}

fun <T, R> T.toModel(classOfT: Class<R>): R? {
    return this.toJson().toModel(classOfT)
}

fun Int?.int(): Int {
    return this ?: 0
}

fun String?.string(): String {
    return this ?: ""
}

inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)

fun <T> Response<T>.getErrorBody(): ErrorResponse? {
    return try {
        this.errorBody()?.string()?.let { error ->
            Gson().fromJson<ErrorResponse>(error)
        }
    } catch (exception: Exception) {
        null
    }
}

fun <T, S> Response<T>.getErrorBody(classOfT: Class<S>): S? {
    return this.errorBody()?.string()?.let { error ->
        error.toModel(classOfT)
    }
}

data class ErrorResponse(
    val code: String? = null,
    val message: String? = null
)

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}
