package com.inyongtisto.myhelper.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


fun <T> Context.intentActivity(activity: Class<T>, value: String, name: String = "extra") {
    val i = Intent(applicationContext, activity)
    i.putExtra(name, value)
    startActivity(i)
}

fun <T> Context.intentActivity(activity: Class<T>) {
    val i = Intent(applicationContext, activity)
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

fun <T> Activity.intentActivityResult(activity: Class<T>, activityResult: ActivityResultLauncher<Intent>) {
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
    setResult(0, intent)
}
