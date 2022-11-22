package com.inyongtisto.helpers

import android.os.*
import com.inyongtisto.helpers.databinding.ActivityMainBinding
import com.inyongtisto.helpers.util.BaseActivity
import com.inyongtisto.helpers.util.Rounded
import com.inyongtisto.myhelper.extension.*
import kotlin.random.Random

class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        Rounded(1015.49, 10.0)
//        setBlackStatusBar()
//        imageView.setImagePicasso()
    }

    private fun initUI() {

        binding.apply {
            val list = listOf("Makan", "Minum", "Tidur")
            spnData.setOnItemSelectedListener(this@MainActivity, list) {

            }

            btnDialogConfim.setOnClickListener {
                showConfirmDialog("Dialog", "Are you okey", "Yes, I'm", percentage = 60) {
                    toastSuccess("Success")
                }
            }
        }

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