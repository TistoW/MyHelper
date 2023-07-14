package com.inyongtisto.helpers

import android.app.Activity
import android.os.*
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.inyongtisto.helpers.databinding.ActivityMainBinding
import com.inyongtisto.helpers.util.BaseActivity
import com.inyongtisto.helpers.util.Rounded
import com.inyongtisto.myhelper.extension.*
import com.inyongtisto.myhelper.util.CountDown
import com.inyongtisto.myhelper.util.RepeatFunction
import java.io.File

class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val repeatFunction = RepeatFunction

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

            btnPrinterExample.setOnClickListener {
//                intentActivity(PrintExampleActivity::class.java)
                imagePicker {
                    profileLauncher.launch(it)
                }
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

    private var fileImage: File? = null
    private val profileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!
            fileImage = File(uri.path!!)
            Glide.with(this).load(fileImage).into(binding.imageView)
        }
    }
}