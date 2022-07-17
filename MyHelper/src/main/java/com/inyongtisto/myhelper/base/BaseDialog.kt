package com.inyongtisto.myhelper.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.inyongtisto.myhelper.extension.getFragmentWidthPercentage

abstract class BaseDialog : DialogFragment() {
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
                getFragmentWidthPercentage(90),
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}