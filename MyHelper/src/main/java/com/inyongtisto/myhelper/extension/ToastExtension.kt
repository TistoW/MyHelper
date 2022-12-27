package com.inyongtisto.myhelper.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.inyongtisto.myhelper.R
import www.sanju.motiontoast.MotionToast

fun Context.toastSimple(message: String?) {
    Toast.makeText(this, message?:"Message", Toast.LENGTH_SHORT).show()
}

fun Activity.toastSuccess(message: String?) {
    MotionToast.darkToast(
        this,
        "Success",
        message?:"Message",
        MotionToast.TOAST_SUCCESS,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.worksans_regular)
    )
}

fun Activity.toastInfo(message: String?) {
    MotionToast.darkToast(
        this,
        "Info",
        message?:"Message",
        MotionToast.TOAST_INFO,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.worksans_regular))
}

fun Activity.toastWarning(message: String?) {
    MotionToast.darkToast(
        this,
        "Warning",
        message?:"Message",
        MotionToast.TOAST_WARNING,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.worksans_regular))
}

fun Activity.toastError(message: String?) {
    MotionToast.darkToast(
        this,
        "Error",
        message?:"Message",
        MotionToast.TOAST_ERROR,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.worksans_regular))
}

fun Fragment.toastSuccess(message: String?){
    requireActivity().toastSuccess(message)
}

fun Fragment.toastInfo(message: String?){
    requireActivity().toastInfo(message)
}

fun Fragment.toastWarning(message: String?){
    requireActivity().toastWarning(message)
}

fun Fragment.toastError(message: String?){
    requireActivity().toastError(message)
}

