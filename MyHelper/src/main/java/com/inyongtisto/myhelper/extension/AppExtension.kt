package com.inyongtisto.myhelper.extension

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.inyongtisto.myhelper.R
import com.inyongtisto.myhelper.util.AppConstants.TIME_STAMP_FORMAT
import java.net.URLEncoder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun visible() = View.VISIBLE
fun invisible() = View.INVISIBLE
fun gone() = View.GONE

const val dateFormat = "yyyy-MM-dd"

fun logs(message: String?) {
    Log.d("RESPONSE", message ?: "message")
}

fun logs(tag: String?, message: String?) {
    Log.d(tag, message ?: "message")
}

fun logs(tag: String?, vararg str: String) {
    var message = ""
    for ((i, s) in str.withIndex()) {
        message += if (i == str.size - 1) s else "$s - "
    }
    Log.d(tag, message)
}

fun loge(message: String?) {
    Log.e("ERROR", message ?: "message")
}

fun loge(tag: String, message: String?) {
    Log.e(tag, message ?: "message")
}

fun longLogs(longString: String, tag: String = "RESPONS") {
    val maxLogSize = 3000
    for (i in 0..longString.length / maxLogSize) {
        val start = i * maxLogSize
        var end = (i + 1) * maxLogSize
        end = if (end > longString.length) longString.length else end
        logs(tag, longString.substring(start, end))
    }
}

private fun <T> List<T>.loga(string: String = "This:") {
    this.forEach {
        logs("$string:" + it.toJson())
    }
}

private fun <T> T.logm(string: String = "This:") {
    logs("$string:" + this.toJson())
}

fun Context.setToolbar(view: Toolbar, title: String) {
    (this as AppCompatActivity).setSupportActionBar(view)
    this.supportActionBar!!.title = title
    this.supportActionBar!!.setDisplayShowHomeEnabled(true)
    this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
}

fun Activity.setTransparantStatusBar() {
    val w: Window = this.window
    w.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}

fun Activity.hidenKeyboard() {
    this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun Fragment.hideKeyboard() {
    val imm: InputMethodManager =
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view!!.windowToken, 0)
}

fun Context.setError(editText: EditText) {
    editText.error = getString(R.string.kolom_tidak_boleh_kosong)
    editText.requestFocus()
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

fun Context.verticalLayoutManager(): LinearLayoutManager {
    val layoutManager = LinearLayoutManager(this)
    layoutManager.orientation = LinearLayoutManager.VERTICAL
    return layoutManager
}

fun Fragment.verticalLayoutManager(): LinearLayoutManager {
    val layoutManager = LinearLayoutManager(requireActivity())
    layoutManager.orientation = LinearLayoutManager.VERTICAL
    return layoutManager
}

fun Context.horizontalLayoutManager(): LinearLayoutManager {
    val layoutManager = LinearLayoutManager(this)
    layoutManager.orientation = LinearLayoutManager.HORIZONTAL
    return layoutManager
}

fun Fragment.horizontalLayoutManager(): LinearLayoutManager {
    val layoutManager = LinearLayoutManager(requireActivity())
    layoutManager.orientation = LinearLayoutManager.HORIZONTAL
    return layoutManager
}


@SuppressLint("QueryPermissionsNeeded")
fun Context.openWhatsApp(phone: String, message: String = "Hallo admin,") {
    try {
        val packageManager: PackageManager = packageManager
        val i = Intent(Intent.ACTION_VIEW)
        val url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode(message, "UTF-8")
        i.setPackage("com.whatsapp")
        i.data = Uri.parse(url)
        if (i.resolveActivity(packageManager) != null) {
            startActivity(i)
        } else {
            openWhatsappAlternate(phone, message)
        }
    } catch (e: Exception) {
        openWhatsappAlternate(phone, message)
    }
}

fun Context.openWhatsappAlternate(nomor: String, message: String) {
    try {
        var toNumber = nomor // contains spaces.
        toNumber = toNumber.replace("+", "").replace(" ", "")
        val sendIntent = Intent("android.intent.action.MAIN")
        sendIntent.putExtra("jid", "$toNumber@s.whatsapp.net")
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.setPackage("com.whatsapp")
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    } catch (e: Exception) {
        toastSimple("Gagal Membuka Whatsapp!")
    }
}

fun Context.openBrowser(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}

fun Context.openEmail(emailTo: String, text: String, subject: String = "") {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:$emailTo"))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        showErrorDialog("tidak ada Aplikasi Emailer terpasang")
    }
}

@SuppressLint("SimpleDateFormat")
fun convertTglUTC(date: String, ygDimau: String): String {
    var hasil = ""
    val frmtlama = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val dateFormat = SimpleDateFormat(frmtlama)
    try {
        val dd = dateFormat.parse(date)
        // tambah 7 jam untuk indonesia
        val hour: Long = 3600 * 1000 // in milli-seconds.
        val newDate = Date(dd!!.time + 7 * hour)
        dateFormat.applyPattern(ygDimau)
        hasil = dateFormat.format(newDate)

    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return hasil
}

fun Context.shareTo(message: String) {
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.putExtra(Intent.EXTRA_TEXT, message)
    intent.type = "text/plain"
    startActivity(Intent.createChooser(intent, "Share to.."))
}

fun Context.copyText(text: String, showToast: Boolean = true) {
    val copyManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val copyText = ClipData.newPlainText("text", text)
    copyManager.setPrimaryClip(copyText)

    if (showToast)
        Toast.makeText(this, "Text Berhasil di salin", Toast.LENGTH_LONG).show()
}

fun Activity.popUpMenu(view: View, list: List<String>, onClicked: (String) -> Unit) {
    val popupMenu = PopupMenu(this, view)
    popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
        onClicked.invoke(it.toString())
        return@OnMenuItemClickListener true
    })
    list.forEach {
        popupMenu.menu.add(it)
    }
    popupMenu.show()
}

fun Context.popUpMenu(view: View, list: List<String>, onClicked: (String) -> Unit) {
    val popupMenu = PopupMenu(this, view)
    popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
        onClicked.invoke(it.toString())
        return@OnMenuItemClickListener true
    })
    list.forEach {
        popupMenu.menu.add(it)
    }
    popupMenu.show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toTimeStamp(format: String = TIME_STAMP_FORMAT): String {
    return DateTimeFormatter.ofPattern(format).format(this)
}

fun TextView.setErrors(
    message: String = "Oopss, gagal memuat data!\nCoba lagi!",
    onClick: (() -> Unit?)? = null
) {
    this.text = message
    this.setOnClickListener {
        onClick?.invoke()
    }
}

fun delayFunction(r: Runnable, duration: Long = 300) {
    Handler(Looper.getMainLooper()).postDelayed(r, duration)
}

@SuppressLint("HardwareIds")
fun Context.getImei(): String {

    fun getDeviceID(): String {
        return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID).toString()
    }

    val imei: String
    val permisI = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
    imei = if (permisI == PackageManager.PERMISSION_GRANTED) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val telephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                telephonyManager.imei
            } catch (e: Exception) {
                getDeviceID()
            }
        } else getDeviceID()
    } else {
        "unknown"
    }

    return imei.uppercase()
}

fun checkPrefix(mPhone: String): String {
    val phone: String = mPhone.substring(0, 4)
    if (phone == "0831" || phone == "0832" || phone == "0833" || phone == "0835" ||
        phone == "0836" || phone == "0837" || phone == "0838" || phone == "0839") {
        return "Axis"
    } else if (phone == "0817" || phone == "0818" || phone == "0819" || phone == "0859" || phone == "0877" || phone == "0878" || phone == "0879") {
        return "XL"
    } else if (phone == "0811" || phone == "0812" || phone == "0813" || phone == "0821" || phone == "0822" || phone == "0823" || phone == "0851" || phone == "0852" || phone == "0853" || phone == "0854") {
        return "Telkomsel"
    } else if (phone == "0814" || phone == "0815" || phone == "0816" || phone == "0855" || phone == "0856" || phone == "0857" || phone == "0858") {
        return "Indosat"
    } else if (phone == "0895" || phone == "0896" || phone == "0897" || phone == "0898" || phone == "0899") {
        return "Tri"
    } else if (phone == "0881" || phone == "0882" || phone == "0883" || phone == "0884" || phone == "0885" || phone == "0886" || phone == "0887" || phone == "0888" || phone == "0889") {
        return "Smartfren"
    } else if (phone == "0828") {
        return "Ceria"
    } else {
        return "non"
    }
}

fun Activity.setPrimaryStatusBarBackgound() {
    setStatusBarBackgroudColor(R.color.colorPrimary)
    lightStatusBar()
}

fun Activity.setWhiteStatusBarBackgound() {
    setStatusBarBackgroudColor(R.color.white)
    blackStatusBar()
}

fun Activity.setStatusBarBackgoundColor(color: Int) {
    setStatusBarBackgroudColor(color)
}

fun Int?.def(v: Int): Int {
    return this ?: v
}

fun String?.def(v: String): String {
    return this ?: v
}

fun Double?.def(v: Double): Double {
    return this ?: v
}

fun Long?.def(v: Long): Long {
    return this ?: v
}