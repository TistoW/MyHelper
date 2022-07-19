package com.inyongtisto.myhelper.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.inyongtisto.myhelper.BuildConfig
import com.inyongtisto.myhelper.util.AppConstants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import java.util.concurrent.ExecutionException


fun <T> Context.intentActivity(activity: Class<T>, value: String, name: String = "extra") {
    val i = Intent(applicationContext, activity)
    i.putExtra(name, value)
    startActivity(i)
}

fun <T> Context.intentActivity(activity: Class<T>) {
    val i = Intent(applicationContext, activity)
    startActivity(i)
}

fun <T> Context.intentActivity(activity: Class<T>, value: Parcelable?, name: String = "extra") {
    val i = Intent(applicationContext, activity)
    i.putExtra(name, value)
    startActivity(i)
}

fun <T> Fragment.intentActivity(activity: Class<T>, value: Parcelable?, name: String = "extra") {
    val i = Intent(requireActivity(), activity)
    i.putExtra(name, value)
    startActivity(i)
}

fun <T> Fragment.intentActivity(activity: Class<T>, value: String, name: String = "extra") {
    val i = Intent(requireActivity(), activity)
    i.putExtra(name, value)
    startActivity(i)
}

fun <T> Fragment.intentActivity(targetClass: Class<T>) {
    startActivity(Intent(requireContext(), targetClass))
}

fun <T> Context.pushActivity(activity: Class<T>, value: String, name: String = "extra") {
    val i = Intent(applicationContext, activity)
    i.putExtra(name, value)
    i.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(i)
}

fun <T> Fragment.pushActivity(activity: Class<T>, value: String, name: String = "extra") {
    val i = Intent(requireActivity(), activity)
    i.putExtra(name, value)
    i.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(i)
}

fun <T> Context.pushActivity(activity: Class<T>) {
    val i = Intent(applicationContext, activity)
    i.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(i)
}

fun <T> Fragment.pushActivity(activity: Class<T>) {
    val i = Intent(requireActivity(), activity)
    i.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(i)
}

fun Activity.getStringExtra(name: String = "extra"): String? {
    return intent.getStringExtra(name)
}

fun <T> Activity.intentActivityResult(
    activity: Class<T>,
    activityResult: ActivityResultLauncher<Intent>
) {
    val intent = Intent(this, activity)
    activityResult.launch(intent)
}

//fun getActivityResult(name: String = "extra", onResultListener: (String) -> Unit): ActivityResultLauncher<Intent> {
//    return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        if (it.resultCode == 0) {
//            val str: String? = it.data?.getStringExtra(name)
//            onResultListener.invoke(str ?: "")
//        }
//    }
//}

fun Activity.sendResult(value: String? = null, name: String = "extra") {
    val intent = Intent()
    if (value != null) {
        intent.putExtra(name, value)
    }
    setResult(Activity.RESULT_OK, intent)
}

// BuildConfig.APPLICATION_ID
// BuildConfig.VERSION_NAME
fun Activity.checkUpdates(
    packageName: String = "com.tisto.smartshop",
    appVersionName: String = "1.0.0"
) {
    var newVersion = ""
    CompositeDisposable().add(Observable.fromCallable {
        try {
            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=$packageName")
                .timeout(30000)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com")
                .get()
                .select(".hAyfc .htlgb")[7]
                .ownText()
        } catch (e:Exception){

        }

    }.subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            if (newVersion.isEmpty()) return@subscribe
            try {
                val mLatestVersionName = newVersion
                logs("version:$appVersionName - $mLatestVersionName")
                if (appVersionName != mLatestVersionName) {
                    val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
                    alertDialog.setTitle("Update Aplikasi")
                    alertDialog.setMessage("Update aplikasi ke versi yang lebih stabil.")
                    alertDialog.setPositiveButton("Update") { dialog, _ ->
                        dialog.dismiss()
                        try {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=$packageName")
                                )
                            )
                        } catch (anfe: ActivityNotFoundException) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                                )
                            )
                        }
                    }
                    alertDialog.setNegativeButton("Tutup") { dialog, _ ->
                        dialog.dismiss()
                    }
                    alertDialog.show()

                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }
        })
}

inline fun <reified T : Any> Activity.extra(key: String = AppConstants.EXTRA, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}

inline fun <reified T : Any> Activity.getExtra(key: String = AppConstants.EXTRA, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}