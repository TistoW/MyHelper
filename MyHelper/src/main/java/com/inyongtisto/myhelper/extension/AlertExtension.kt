package com.inyongtisto.myhelper.extension

import android.content.Context
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.inyongtisto.myhelper.R

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


