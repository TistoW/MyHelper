package com.inyongtisto.helpers.util

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inyongtisto.myhelper.extension.appLoadingDialog
import com.inyongtisto.myhelper.util.AppProgressDialog

abstract class BaseActivity : AppCompatActivity() {

    lateinit var progress: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupProgress()
    }

    private fun setupProgress() {
        progress = appLoadingDialog()
    }
}