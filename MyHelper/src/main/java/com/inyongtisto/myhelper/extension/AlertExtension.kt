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
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showSuccessDialog(message: String, onConfirmClickListener: () -> Unit) {
    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        .setTitleText(getString(R.string.berhasil))
        .setContentText(message)
        .setConfirmText(getString(R.string.dialog_ok))
        .setConfirmClickListener {
            it.dismiss()
            onConfirmClickListener()
        }
        .show()
}

fun Context.showErrorDialog(message: String, title: String = getString(R.string.oopss)) {
    SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        .setTitleText(title)
        .setConfirmText(getString(R.string.dialog_ok))
        .setContentText(message)
        .show()
}

fun Context.showSuccessDialog(title: String, pesan: String) {
    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        .setTitleText(title)
        .setContentText(pesan)
        .show()
}

fun Context.showInfoDialog(title: String, pesan: String) {
    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        .setTitleText(title)
        .setContentText(pesan)
        .show()
}


