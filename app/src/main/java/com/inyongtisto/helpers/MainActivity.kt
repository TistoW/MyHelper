package com.inyongtisto.helpers

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.datepicker.MaterialDatePicker
import com.inyongtisto.helpers.util.BaseActivity
import com.inyongtisto.myhelper.PullRefresh
import com.inyongtisto.myhelper.extension.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PullRefresh(swipeRefresh) {
            progress.show()
            Handler(Looper.myLooper()!!).postDelayed({
                progress.dismiss()
                swipeRefresh.isRefreshing = false
            }, 2000)
        }

        val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        btn_simpan.setOnClickListener {

        }

        spn.setOnPositionSelectedListener(this, arrayListOf()) {

        }
//        dialogDatePicker {
//            logs("SelectedDate:" + it.toTimeStamp())
//        }
    }

    var fileImage: File? = null
    private val profileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                fileImage = File(uri.path!!)
            }
        }
}