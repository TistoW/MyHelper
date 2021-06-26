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
import com.inyongtisto.myhelper.R
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import es.dmoral.toasty.Toasty
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Context.toastSimple(pesan: String) {
    Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
}

fun Context.toastSuccess(pesan: String) {
    Toasty.success(this, pesan, Toast.LENGTH_SHORT, true).show()
}

fun Context.toastInfo(pesan: String) {
    Toasty.info(this, pesan, Toast.LENGTH_SHORT, true).show()
}

fun Context.toastError(pesan: String) {
    Toasty.error(this, pesan, Toast.LENGTH_SHORT, true).show()
}


