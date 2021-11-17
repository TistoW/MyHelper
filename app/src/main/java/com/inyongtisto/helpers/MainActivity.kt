package com.inyongtisto.helpers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.inyongtisto.helpers.util.BaseActivity
import com.inyongtisto.myhelper.PullRefresh
import com.inyongtisto.myhelper.extension.*
import kotlinx.android.synthetic.main.activity_main.*

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

        btn_simpan.setOnClickListener {
            progress.dismiss()
        }
    }
}