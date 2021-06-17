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
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun visible() = View.VISIBLE
fun invisible() = View.INVISIBLE
fun gone() = View.GONE

const val dateFormat = "yyyy-MM-dd"

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun logs(message: String) {
    Log.d("RESPONS", message)
}

fun Context.setToolbar(toolbar: Toolbar, title: String) {
    (this as AppCompatActivity).setSupportActionBar(toolbar)
    this.supportActionBar!!.title = title
    this.supportActionBar!!.setDisplayShowHomeEnabled(true)
    this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
}

fun Activity.hidenKeyboard() {
    this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun Context.setError(editText: EditText) {
    editText.error = getString(R.string.kolom_tidak_boleh_kosong)
    editText.requestFocus()
}

fun EditText.setEmptyError() {
    this.error = context.getString(R.string.kolom_tidak_boleh_kosong)
    this.requestFocus()
}

fun EditText.isEmpty(setError: Boolean = true): Boolean {
    return if (this.text.isEmpty()) {
        if (setError){
            this.error = context.getString(R.string.kolom_tidak_boleh_kosong)
            this.requestFocus()
        }
        true
    } else false
}

fun EditText.clearText() {
    setText("")
    clearFocus()
//    context.hideKeyboard(this)
}

fun AppCompatEditText.clearSearch() {
    setText("")
    clearFocus()
//    context.hideKeyboard(this)
}

fun Activity.isCameraPermissionGranted(context: Context, REQUEST_PERMISSION_CAMERA: Int): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        if (context.checkSelfPermission(Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            // Show the permission request
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION_CAMERA
            )
            false
        }
    } else {
        true
    }
}

fun getFragmentWidthPercentage(percentage: Int): Int {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    return (rect.width() * percent).toInt()
}

//fun Context.showSuccessDialog(message: String, onConfirmClickListener: () -> Unit) {
//    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
//            .setTitleText(getString(R.string.berhasil))
//            .setContentText(message)
//            .setConfirmText(getString(R.string.dialog_ok))
//            .setConfirmClickListener {
//                onConfirmClickListener()
//            }
//            .show()
//}

//fun Context.showErrorDialog(message: String) {
//    SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
//            .setTitleText(getString(R.string.oopss))
//            .setConfirmText(getString(R.string.dialog_ok))
//            .setContentText(message)
//            .show()
//}

fun priceFormatter(price: Int): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(price)
}

//fun RecyclerView.setItemSeparator() {
//    val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//    divider.setDrawable(context.getDrawableCompat(R.drawable.item_separator)!!)
//    addItemDecoration(divider)
//}

fun AppCompatEditText.addCustomTextWatcher(button: View) {

    context.getColor(android.R.color.holo_blue_bright)
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            button.isEnabled = !s.isNullOrEmpty()
        }
    })
}