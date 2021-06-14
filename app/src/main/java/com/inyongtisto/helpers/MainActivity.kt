package com.inyongtisto.helpers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.inyongtisto.myhelper.extension.logs
import com.inyongtisto.myhelper.extension.remove
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_simpan.setOnClickListener {
            Log.d("TAG", "onCreate: ")
        }

        logs("apaini".remove("ini"))
    }
}