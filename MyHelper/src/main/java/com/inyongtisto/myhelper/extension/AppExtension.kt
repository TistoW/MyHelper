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
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.inyongtisto.myhelper.R
import java.text.DecimalFormat

fun visible() = View.VISIBLE
fun invisible() = View.INVISIBLE
fun gone() = View.GONE

const val dateFormat = "yyyy-MM-dd"

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

//fun Context.setError(editText: EditText) {
//    editText.error = getString(R.string.kolom_tidak_boleh_kosong)
//    editText.requestFocus()
//}
//
//fun EditText.setEmptyError() {
//    this.error = context.getString(R.string.kolom_tidak_boleh_kosong)
//    this.requestFocus()
//}
//
//fun AppCompatEditText.setEmptyError() {
//    this.error = context.getString(R.string.kolom_tidak_boleh_kosong)
//    this.requestFocus()
//}


fun Activity.isCameraPermissionGranted(context: Context, REQUEST_PERMISSION_CAMERA : Int): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        if (context.checkSelfPermission(Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            // Show the permission request
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                    REQUEST_PERMISSION_CAMERA)
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

//fun EditText.clearSearch() {
//    setText("")
//    clearFocus()
//    context.hideKeyboard(this)
//}
//
//fun AppCompatEditText.clearSearch() {
//    setText("")
//    clearFocus()
//    context.hideKeyboard(this)
//}

fun String.removeComma(): String {
    return replace(",", "")
}

fun String.fixPhoneNumber(): String {
    return when {
        take(1) == "0" -> "62${this.substring(1, this.length)}"
        take(2) == "62" -> this
        isNullOrEmpty() -> this
        else -> "62$this"
    }
//    return if (this.take(1) == "0") {
//        "62${this.substring(1, this.length)}"
//    } else {
//        "62$this"
//    }
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
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            button.isEnabled = !s.isNullOrEmpty()
        }
    })
}