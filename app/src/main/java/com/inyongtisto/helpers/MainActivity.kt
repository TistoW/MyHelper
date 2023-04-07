package com.inyongtisto.helpers

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.*
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.inyongtisto.helpers.databinding.ActivityMainBinding
import com.inyongtisto.helpers.printExample.PrinterAdapter
import com.inyongtisto.helpers.printExample.PrinterDevice
import com.inyongtisto.helpers.util.BaseActivity
import com.inyongtisto.helpers.util.Rounded
import com.inyongtisto.myhelper.extension.*
import com.inyongtisto.myhelper.printer.Alignment
import com.inyongtisto.myhelper.printer.Size
import com.inyongtisto.myhelper.printer.ThermalPrinter
import com.inyongtisto.myhelper.printer.Style
import kotlin.concurrent.thread

class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        Rounded(1015.49, 10.0)
    }

    private fun initUI() {

        binding.apply {
            val list = listOf("Makan", "Minum", "Tidur")
            spnData.setOnItemSelectedListener(this@MainActivity, list) {

            }

            btnDialogConfim.setOnClickListener {
                openWhatsApp("6282341810186", "Test Haloo")
            }
        }

//        setBlackStatusBar()
//        imageView.setImagePicasso()


//        val date = CustomDate
//        logs("Today:${date.getToday()}")
//        logs("firstDayOfThisWeek:${date.getFirstDayOfThisWeek()}")
//        logs("lastDayOfThisWeek:${date.getLastDayOfThisWeek()}")
//        logs("firstDayOfLastWeek:${date.getFirstDayOfLastWeek()}")
//        logs("lastDayOfLastWeek:${date.getLastDayOfLastWeek()}")
//
//        logs("firstDayOfThisMonth:${firstDayOfThisMonth()}")
//        logs("lastDayOfThisMonth:${lastDayOfThisMonth()}")
//
//        logs("firstDayOfLastMonth:${firstDayOfLastMonth()}")
//        logs("lastDayOfLastMonth:${lastDayOfLastMonth()}")
//
//        logs("last30Day:${last30Day()}")
//        logs("next30Day:${next30Day()}")
//
//        logs("last7Day:${last7Day()}")
//        logs("next7Day:${next7Day()}")
//
//        logs("tomorrow:${tomorrow()}")
//        logs("yesterday:${yesterday()}")
//
//        logs("nextDay:${nextDay(10)}")
//
//        val currentTime = currentTimeUTC(isUTCTime = false)
//        logs("time:$currentTime")
//        logs("timeToUTC:${currentTimeUTC()}")
//        logs("timeFromUTC:" + currentTimeUTC().convertFromUTC())

    }
}