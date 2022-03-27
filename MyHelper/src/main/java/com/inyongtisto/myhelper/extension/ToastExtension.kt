package com.inyongtisto.myhelper.extension

import android.Manifest
import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.inyongtisto.myhelper.R
import www.sanju.motiontoast.MotionToast

fun Context.toastSimple(pesan: String) {
    Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
}

fun Activity.toastSuccess(pesan: String) {
    MotionToast.darkToast(
        this,
        "Success",
        pesan,
        MotionToast.TOAST_SUCCESS,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.worksans_regular)
    )
}

fun Activity.toastInfo(pesan: String) {
    MotionToast.darkToast(
        this,
        "Info",
        pesan,
        MotionToast.TOAST_INFO,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.worksans_regular))
}

fun Activity.toastWarning(pesan: String) {
    MotionToast.darkToast(
        this,
        "Warning",
        pesan,
        MotionToast.TOAST_WARNING,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.worksans_regular))
}

fun Activity.toastError(pesan: String) {
    MotionToast.darkToast(
        this,
        "Error",
        pesan,
        MotionToast.TOAST_ERROR,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.worksans_regular))
}

fun Fragment.toastSuccess(pesan: String){
    requireActivity().toastSuccess(pesan)
}

fun Fragment.toastInfo(pesan: String){
    requireActivity().toastInfo(pesan)
}

fun Fragment.toastWarning(pesan: String){
    requireActivity().toastWarning(pesan)
}

fun Fragment.toastError(pesan: String){
    requireActivity().toastError(pesan)
}

