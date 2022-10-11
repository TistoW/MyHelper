package com.inyongtisto.myhelper.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.inyongtisto.myhelper.util.ConfirmDialogFragment


fun AppCompatActivity.showConfirmDialog(
    title: String,
    subtitle: String,
    actionText: String = "Ok",
    actionTextSecondary: String? = null,
    cancellable: Boolean = true,
    percentage: Int = 80,
    onActionSecondary: (() -> Unit)? = null,
    onAction: (() -> Unit)? = null) {
    ConfirmDialogFragment(title, subtitle, actionText,actionTextSecondary, cancellable, percentage,onActionSecondary, onAction).show(supportFragmentManager, ConfirmDialogFragment.TAG)
}

fun Fragment.showConfirmDialog(
    title: String,
    subtitle: String,
    actionText: String = "Ok",
    actionTextSecondary: String? = null,
    cancellable: Boolean = true,
    percentage: Int = 80,
    onActionSecondary: (() -> Unit)? = null,
    onAction: (() -> Unit)? = null) {
    ConfirmDialogFragment(title, subtitle, actionText,actionTextSecondary, cancellable, percentage,onActionSecondary, onAction).show(childFragmentManager, ConfirmDialogFragment.TAG)
}