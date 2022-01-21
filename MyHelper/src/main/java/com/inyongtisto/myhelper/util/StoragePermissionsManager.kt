package com.inyongtisto.myhelper.util

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.inyongtisto.myhelper.R
import com.inyongtisto.myhelper.extension.logs

class StoragePermissionsManager(
    val context: Activity,
    private var launcherResult: ActivityResultLauncher<Intent>,
    private var launcherPermissions: ActivityResultLauncher<Array<String>>
) {

//    private val launcherResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        if (permissions.isGranted(it)) {
//            //Permission granted
//        } else permissions.showDialogReq() //Permission not granted
//    }
//
//    private val launcherPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
//        permissions.isGranted(result)
//        if (permissions.isGranted(result)) {
//            //Permission granted
//        } else this.permissions.showDialogReq()  //Permission not granted
//    }

    fun getPermissionStatus(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val status = Environment.isExternalStorageManager()
            logs("checking status Android R above:$status")
            return status
        } else {
            if (checkSelfPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE) || checkSelfPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                if (shouldShowRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE) || shouldShowRequest(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    logs("Show Information about why you need the permission")
                    getAlertDialog()
                } else {
                    //request the permission
                    logs("request the permission showing dialog")
                    showDialogReq()
                }
            } else return true
        }
        return false
    }

    fun isGranted(result: MutableMap<String, Boolean>): Boolean {
        return result[Manifest.permission.READ_EXTERNAL_STORAGE] == true && result[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true
    }

    fun isGranted(it: ActivityResult): Boolean {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) return true //Permission granted
            } else return false //Permission not granted
        }
        return true
    }

    private var permissionsRequired = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    fun askPermissions(): Boolean {
        val status = getPermissionStatus()
        if (!status) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                logs("Asking Permissions android Above Android R")
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.addCategory("android.intent.category.DEFAULT")
                    intent.data = Uri.parse(String.format("package:%s", context.applicationContext.packageName))
                    launcherResult.launch(intent)
                } catch (e: Exception) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    launcherResult.launch(intent)
                }
            } else {
                logs("Asking Permissions")
                //below android 11
                launcherPermissions.launch(permissionsRequired)
            }
        }
        return status
    }

    private fun checkSelfPermissions(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRequest(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(context, permission)
    }

    fun showDialogReq() {
        android.app.AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.permission_dialog_message))
                .setPositiveButton(context.getString(R.string.izinkan)) { it, _ ->
                    it.dismiss()
                    askPermissions()
                }
                .setNegativeButton(context.getString(R.string.tutup)) { _, _ -> context.finish() }
                .create().show()
    }

    private fun getAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.permission_warning_dialog))
        builder.setMessage(context.getString(R.string.app_need_some_permission))
        builder.setPositiveButton(context.getString(R.string.izinkan)) { dialog, _ ->
            dialog.dismiss()
            askPermissions()
        }
        builder.setNegativeButton(context.getString(R.string.tutup)) { dialog, _ ->
            dialog.dismiss()
            context.finish()
        }
        builder.show()
    }

}