package com.inyongtisto.helpers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import com.inyongtisto.myhelper.OnEditTextChanged
import com.inyongtisto.myhelper.PullRefresh
import com.inyongtisto.myhelper.base.BaseActivity
import com.inyongtisto.myhelper.extension.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PullRefresh(swipeRefresh) {
            logs("do this on this")
            toastSuccess("Success Dongs")
            swipeRefresh.isRefreshing = false

//            showErrorDialog("Error dialogs test")
            showLoading()
            Handler(Looper.myLooper()!!).postDelayed({
                dismisLoading()
            }, 2000)
        }

        btn_simpan.setOnClickListener {
            dismisLoading()
        }
    }
}