package com.inyongtisto.myhelper.extension

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.inyongtisto.myhelper.R

fun Context.checkingSelfPermission(readExternalStorage: String): Boolean {
    return ContextCompat.checkSelfPermission(this, readExternalStorage) == PackageManager.PERMISSION_GRANTED
}

fun Activity.shouldShowRequest(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}

fun Activity.showDialogReq(onClick: (() -> Unit)? = null) {
    android.app.AlertDialog.Builder(this)
            .setMessage(this.getString(R.string.permission_dialog_message))
            .setPositiveButton(this.getString(R.string.izinkan)) { it, _ ->
                it.dismiss()
                onClick?.invoke()
            }
            .setNegativeButton(this.getString(R.string.tutup)) { _, _ -> this.finish() }
            .create().show()
}

fun Activity.getAlertDialog(onClick: (() -> Unit)? = null) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(this.getString(com.inyongtisto.myhelper.R.string.permission_warning_dialog))
    builder.setMessage(this.getString(com.inyongtisto.myhelper.R.string.app_need_some_permission))
    builder.setPositiveButton(this.getString(com.inyongtisto.myhelper.R.string.izinkan)) { dialog, _ ->
        dialog.dismiss()
        onClick?.invoke()
    }
    builder.setNegativeButton(this.getString(com.inyongtisto.myhelper.R.string.tutup)) { dialog, _ ->
        dialog.dismiss()
        this.finish()
    }
    builder.show()
}

fun Activity.dialogExplain() {
    val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
    dialog.setMessage("Izin tidak diberikan tutup aplikasi")
            .setPositiveButton("Yes") { _, _ ->
                finish()
            }.show()
}