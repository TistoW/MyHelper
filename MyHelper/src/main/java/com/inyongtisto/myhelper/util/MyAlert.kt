package com.inyongtisto.myhelper.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.inyongtisto.myhelper.R
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog

object MyAlert {

    fun error(context: Activity, title: String, pesan: String) {
        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(pesan)
            .show()
    }

    fun success(context: Activity, title: String, pesan: String) {
        SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(title)
            .setContentText(pesan)
            .show()
    }

    fun info(context: Activity, title: String, pesan: String) {
        SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(title)
            .setContentText(pesan)
            .show()
    }

    fun info(
        context: Activity,
        title: String,
        pesan: String,
        confirmText: String,
        cancelText: String,
        callback: Callbacks
    ) {
        val pDialog = SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
        pDialog.setTitleText(title)
            .setContentText(pesan)
            .setConfirmText(confirmText)
            .setCancelText(cancelText)
            .setConfirmClickListener {
                callback.onConfirmCliked()
                pDialog.dismiss()
            }
            .setCancelClickListener {
                callback.onCancelCliked()
                pDialog.dismiss()
            }
            .show()
    }


    interface Callbacks {

        fun onConfirmCliked()

        fun onCancelCliked()

        fun onFilterClicked(status: String)
    }

    open class DefaultCallback : Callbacks {
        override fun onConfirmCliked() {}

        override fun onCancelCliked() {}

        override fun onFilterClicked(status: String) {}
    }
}