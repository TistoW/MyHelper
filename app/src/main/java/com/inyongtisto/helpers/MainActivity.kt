package com.inyongtisto.helpers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.inyongtisto.myhelper.OnEditTextChanged
import com.inyongtisto.myhelper.extension.logs
import com.inyongtisto.myhelper.PullRefresh
import com.inyongtisto.myhelper.extension.toastSuccess
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PullRefresh(swipeRefresh) {
            logs("do this on this")
            toastSuccess("Success Dongs")
            swipeRefresh.isRefreshing = false
        }
    }
}