package com.inyongtisto.myhelper.extension

import android.graphics.Paint
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.updateLayoutParams
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import com.inyongtisto.myhelper.R

fun TextView.coret() {
    this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextView.unCoret() {
    this.paintFlags = 0
}

fun TextView.setLeftDrawable(@DrawableRes id: Int = 0) {
    this.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
}

fun TextView.setLeftDrawableTint(id: Int = R.color.colorPrimary) {
    compoundDrawables.first().setTint(context.getColor(id))
}

fun TextView.setDrawableEndTint(id: Int = R.color.colorPrimary) {
    try {
        compoundDrawables[1].setTint(context.getColor(id))
    } catch (e: Exception) {
        logs("tint color error:" + e.message)
    }
}

fun MaterialCardView.setBackgroundTint(color: Int) {
    background.setTint(context.getColor(color))
}

fun TextInputLayout.setCenterPrefix() {
    prefixTextView.updateLayoutParams {
        height = ViewGroup.LayoutParams.MATCH_PARENT
    }
    prefixTextView.gravity = Gravity.CENTER_VERTICAL
}