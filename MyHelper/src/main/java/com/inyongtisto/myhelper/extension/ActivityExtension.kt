package com.inyongtisto.myhelper.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.InsetDrawable
import android.net.Uri
import android.os.Parcelable
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.inyongtisto.myhelper.util.AppConstants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import java.util.concurrent.ExecutionException
import kotlin.math.roundToInt


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

fun <T> Context.createIntent(activity: Class<T>): Intent {
    return Intent(applicationContext, activity)
}

fun <T> Context.createIntent(activity: Class<T>, value: String, name: String = "extra"): Intent {
    val i = Intent(applicationContext, activity)
    i.putExtra(name, value)
    return i
}

fun <T> Context.createIntent(activity: Class<T>, value: Parcelable?, name: String = "extra"): Intent {
    val i = Intent(applicationContext, activity)
    i.putExtra(name, value)
    return i
}

fun <T> Fragment.createIntent(activity: Class<T>): Intent {
    return Intent(requireActivity(), activity)
}

fun <T> Fragment.createIntent(activity: Class<T>, value: String, name: String = "extra"): Intent {
    val i = Intent(requireActivity(), activity)
    i.putExtra(name, value)
    return i
}

fun <T> Fragment.createIntent(activity: Class<T>, value: Parcelable?, name: String = "extra"): Intent {
    val i = Intent(requireActivity(), activity)
    i.putExtra(name, value)
    return i
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
        } catch (e: Exception) {

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

fun Activity.getRootView(): View {
    return findViewById(android.R.id.content)
}

fun Context.convertDpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )
}

fun Activity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    val marginOfError = this.convertDpToPx(50F).roundToInt()
    return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}

fun Activity.onKeyboardShowListener(isShow: (Boolean) -> Unit): ViewTreeObserver.OnGlobalLayoutListener {

    /* example variable

        private var _listener: ViewTreeObserver.OnGlobalLayoutListener? = null
        private val listener get() = _listener!!

        // onCreateView

        _listener = keyboardShowListener {
            logs("isShow:$it")
        }

     */

    return object : ViewTreeObserver.OnGlobalLayoutListener {
        // Keep a reference to the last state of the keyboard
        private var lastState: Boolean = isKeyboardOpen()

        /**
         * Something in the layout has changed
         * so check if the keyboard is open or closed
         * and if the keyboard state has changed
         * save the new state and invoke the callback
         */
        override fun onGlobalLayout() {
            logs("Keyboard listener")
            val isOpen = isKeyboardOpen()
            if (isOpen == lastState) {
                isShow.invoke(isOpen)
                logs("Keyboard is close")
                return
            } else {
                logs("keyboard is Open")
                isShow.invoke(isOpen)
                lastState = isOpen
            }
        }
    }
}

fun Context.openMap(latitude: Double, longitude: Double) {
    val uri = "http://maps.google.com/maps?q=loc:$latitude,$longitude"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    intent.setPackage("com.google.android.apps.maps")
    startActivity(intent)
}

fun Fragment.openMap(latitude: Double, longitude: Double) {
    requireActivity().openMap(latitude, longitude)
}

class MenuImage(
    var name: String,
    @DrawableRes var image: Int
)

@SuppressLint("RestrictedApi")
fun Activity.popUpMenuImage(view: View, list: List<MenuImage>, onClicked: (String) -> Unit) {
    val popupMenu = PopupMenu(this, view)
    list.forEach {
        popupMenu.menu.add(it.name).icon = AppCompatResources.getDrawable(this, it.image)
    }
    if (popupMenu.menu is MenuBuilder) {
        val menuBuilder = popupMenu.menu as MenuBuilder
        menuBuilder.setOptionalIconsVisible(true)
        for (item in menuBuilder.visibleItems) {
            val iconMarginPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                AppConstants.ICON_MARGIN.toFloat(),
                resources.displayMetrics
            ).toInt()
            if (item.icon != null) {
                item.icon = InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0)
            }
        }
    }
    popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
        onClicked.invoke(it.toString())
        return@OnMenuItemClickListener true
    })

    popupMenu.show()
}

fun Activity.imagePicker(
    width: Int = 1080,
    height: Int = 1080,
    compress: Int = 1024,
    isCrop: Boolean = true,
    onlyCamera: Boolean = false,
    onlyGallery: Boolean = false,
    intent: (Intent) -> Unit
) {
    val picker = ImagePicker
            .with(this)
            .maxResultSize(width, height)

    if (compress > 0) picker.compress(compress)
    if (isCrop) picker.crop()
    if (onlyCamera) picker.cameraOnly()
    if (onlyGallery) picker.galleryOnly()
    picker.createIntent {
        intent.invoke(it)
    }
}