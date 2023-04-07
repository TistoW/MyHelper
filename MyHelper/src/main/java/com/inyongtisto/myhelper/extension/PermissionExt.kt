package com.inyongtisto.myhelper.extension

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
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

fun Activity.shouldShowRequests(arrayOf: Array<String>): Boolean {
    arrayOf.forEach {
        return shouldShowRequest(it)
    }
    return false
}

val bluetoothPermission = arrayOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.BLUETOOTH,
    Manifest.permission.BLUETOOTH_ADMIN,
)

@RequiresApi(api = Build.VERSION_CODES.S)
val android12BluetoothPermission = arrayOf(
    Manifest.permission.BLUETOOTH_SCAN,
    Manifest.permission.BLUETOOTH_CONNECT,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.BLUETOOTH,
    Manifest.permission.BLUETOOTH_ADMIN,
)

fun Activity.requestBluetoothPermissions(requestBluetooth: ActivityResultLauncher<Intent>) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        ActivityCompat.requestPermissions(this, android12BluetoothPermission, 200)
    } else {
        ActivityCompat.requestPermissions(this, bluetoothPermission, 200)
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        requestBluetooth.launch(enableBtIntent)

//            private val requestBluetooth = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                if (result.resultCode == RESULT_OK) {
//                    //granted
//                    logs("cek ini?")
//                } else {
//                    dialogExplain()
//                }
//            }

//        @RequiresApi(Build.VERSION_CODES.S)
//        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//            if (requestCode == 200) {
//                var allGranted = false
//                for (i in grantResults.indices) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        allGranted = true
//                    } else {
//                        allGranted = false
//                        break
//                    }
//                }
//
//                if (allGranted) { // generated
//
//                } else if (shouldShowRequests(android12BluetoothPermission)) {
//                    getAlertDialog {
//
//                    }
//                } else {
//                    dialogExplain()
//                }
//            }
//        }
    }
}


fun Activity.dialogExplain() {
    val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
    dialog.setMessage("Izin tidak diberikan tutup aplikasi")
            .setPositiveButton("Yes") { _, _ ->
                finish()
            }.show()
}