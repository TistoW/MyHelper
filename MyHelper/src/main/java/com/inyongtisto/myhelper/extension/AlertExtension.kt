package com.inyongtisto.myhelper.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.inyongtisto.myhelper.R
import com.inyongtisto.myhelper.loading.MyLoading

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

fun Context.showSuccessDialog(title: String, pesan: String) {
    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        .setTitleText(title)
        .setContentText(pesan)
        .show()
}

fun Context.showErrorDialog(message: String, title: String = getString(R.string.oopss)) {
    SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        .setTitleText(title)
        .setConfirmText(getString(R.string.dialog_ok))
        .setContentText(message)
        .show()
}

fun Context.showErrorDialog(
    message: String,
    title: String = getString(R.string.oopss),
    onClicked: () -> Unit
) {
    SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        .setTitleText(title)
        .setConfirmText(getString(R.string.dialog_ok))
        .setContentText(message)
        .setConfirmClickListener {
            it.dismiss()
            onClicked()
        }
        .show()
}

fun Context.showInfoDialog(title: String, pesan: String) {
    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        .setTitleText(title)
        .setContentText(pesan)
        .show()
}

fun Context.showInfoDialog(title: String, pesan: String, onConfirmClickListener: () -> Unit) {
    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        .setTitleText(title)
        .setContentText(pesan)
        .setConfirmClickListener {
            it.dismiss()
            onConfirmClickListener()
        }
        .show()
}

fun Fragment.showSuccessDialog(message: String, onConfirmClickListener: () -> Unit) {
    requireActivity().showSuccessDialog(message, onConfirmClickListener)
}

fun Fragment.showSuccessDialog(title: String, pesan: String) {
    requireActivity().showSuccessDialog(title, pesan)
}

fun Fragment.showErrorDialog(title: String, pesan: String = getString(R.string.oopss)) {
    requireActivity().showErrorDialog(title, pesan)
}

fun Fragment.showErrorDialog(
    message: String,
    pesan: String = getString(R.string.oopss),
    onConfirm: () -> Unit
) {
    requireActivity().showErrorDialog(message, pesan, onConfirm)
}

fun Fragment.showInfoDialog(title: String, pesan: String) {
    requireActivity().showInfoDialog(title, pesan)
}

fun Fragment.showInfoDialog(
    message: String,
    pesan: String,
    onConfirm: () -> Unit
) {
    requireActivity().showInfoDialog(message, pesan, onConfirm)
}

fun Activity.showLoading() {
    val inflater = layoutInflater
    val layout = inflater.inflate(R.layout.view_loading, null)
    val alertDialog: AlertDialog = MyLoading.newInstance(this)
    alertDialog.setView(layout)
    alertDialog.setCancelable(false)
    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.show()
}

fun Activity.dismisLoading() {
    val alertDialog: AlertDialog = MyLoading.newInstance(this)
    alertDialog.dismiss()
}

fun Activity.appLoadingDialog(): AlertDialog {
    val inflater = layoutInflater
    val layout = inflater.inflate(R.layout.view_loading, null)
    val alertDialog: AlertDialog = MyLoading.newInstance(this)
    alertDialog.setView(layout)
    alertDialog.setCancelable(false)
    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return alertDialog
}