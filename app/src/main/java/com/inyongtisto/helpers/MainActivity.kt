package com.inyongtisto.helpers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inyongtisto.myhelper.extension.logs
import com.inyongtisto.myhelper.PullRefresh
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PullRefresh(swipeRefresh) {
            logs("do this on this")
        }

        swipeRefresh.setOnRefreshListener {

        }
        swipeRefresh.setColorSchemeColors()
    }
}