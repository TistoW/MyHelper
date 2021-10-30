package com.inyongtisto.myhelper.loading

import android.app.Activity
import android.app.AlertDialog
import com.inyongtisto.myhelper.extension.showLoading

class MyLoading {
    companion object {
        private var alertDialog: AlertDialog? = null
        fun newInstance(activity: Activity): AlertDialog {
            if (alertDialog == null) {
                alertDialog = AlertDialog.Builder(activity).create()
            }
            return alertDialog!!
        }
    }
}