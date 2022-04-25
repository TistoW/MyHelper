package com.inyongtisto.helpers

import android.Manifest
import android.app.Activity
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.inyongtisto.helpers.databinding.ActivityMainBinding
import com.inyongtisto.helpers.util.BaseActivity
import com.inyongtisto.helpers.util.Rounded
import com.inyongtisto.myhelper.PullRefresh
import com.inyongtisto.myhelper.extension.*
import com.inyongtisto.myhelper.util.EditTextSearchListener
import com.inyongtisto.myhelper.util.StoragePermissionsManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

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
        }
    }
}