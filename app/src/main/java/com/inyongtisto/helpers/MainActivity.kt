package com.inyongtisto.helpers

import android.os.*
import android.view.ViewTreeObserver
import com.inyongtisto.helpers.databinding.ActivityMainBinding
import com.inyongtisto.helpers.util.BaseActivity
import com.inyongtisto.helpers.util.Rounded
import com.inyongtisto.myhelper.extension.*

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
                showConfirmDialog("Dialog", "Are you okey", "Yes, I'm") {
                    toastSuccess("Success")
                }
            }
        }

        val date = CustomDate
        logs("Today:${date.getToday()}")
        logs("firstDayOfThisWeek:${date.getFirstDayOfThisWeek()}")
        logs("lastDayOfThisWeek:${date.getLastDayOfThisWeek()}")
        logs("firstDayOfLastWeek:${date.getFirstDayOfLastWeek()}")
        logs("lastDayOfLastWeek:${date.getLastDayOfLastWeek()}")

        logs("firstDayOfThisMonth:${firstDayOfThisMonth()}")
        logs("lastDayOfThisMonth:${lastDayOfThisMonth()}")

        logs("firstDayOfLastMonth:${firstDayOfLastMonth()}")
        logs("lastDayOfLastMonth:${lastDayOfLastMonth()}")

        logs("last30Day:${last30Day()}")
        logs("next30Day:${next30Day()}")

        logs("last7Day:${last7Day()}")
        logs("next7Day:${next7Day()}")

        logs("tomorrow:${tomorrow()}")
        logs("yesterday:${yesterday()}")

        logs("nextDay:${nextDay(10)}")

        _listener = onKeyboardShowListener {
            logs("isShow:$it")
        }
    }

    private var _listener: ViewTreeObserver.OnGlobalLayoutListener? = null
    private val listener get() = _listener!!

    override fun onResume() {
        super.onResume()
        val view = binding.root
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }
}