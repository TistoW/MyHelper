package com.inyongtisto.myhelper.extension

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Rect
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import com.inyongtisto.myhelper.R
import www.sanju.motiontoast.MotionToast
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Context.toastSimple(pesan: String) {
    Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
}

fun Activity.toastSuccess(pesan: String) {
//    Toasty.success(this, pesan, Toast.LENGTH_SHORT, true).show()
    MotionToast.createToast(
        this,
        "Success",
        pesan,
        MotionToast.TOAST_SUCCESS,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.helvetica_regular)
    )
}

fun Activity.toastInfo(pesan: String) {
    MotionToast.createToast(
        this,
        "Info",
        pesan,
        MotionToast.TOAST_INFO,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.helvetica_regular))
}

fun Activity.toastWarning(pesan: String) {
    MotionToast.createToast(
        this,
        "Warning",
        pesan,
        MotionToast.TOAST_WARNING,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.helvetica_regular))
}

fun Activity.toastError(pesan: String) {
    MotionToast.createToast(
        this,
        "Error",
        pesan,
        MotionToast.TOAST_ERROR,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.helvetica_regular))
}


