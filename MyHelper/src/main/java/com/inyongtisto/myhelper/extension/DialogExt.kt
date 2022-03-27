package com.inyongtisto.myhelper.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.inyongtisto.myhelper.util.ConfirmDialogFragment


fun AppCompatActivity.showConfirmDialog(
    title: String,
    subtitle: String,
    actionText: String = "Ok",
    onAction: (() -> Unit)? = null) {
    ConfirmDialogFragment(title, subtitle, actionText, onAction).show(supportFragmentManager, ConfirmDialogFragment.TAG)
}

fun Fragment.showConfirmDialog(
    title: String,
    subtitle: String,
    actionText: String = "Ok",
    onAction: (() -> Unit)? = null) {
    ConfirmDialogFragment(title, subtitle, actionText, onAction).show(childFragmentManager, ConfirmDialogFragment.TAG)
}