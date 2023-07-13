package com.inyongtisto.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.*
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.inyongtisto.helpers.databinding.ActivityMainBinding
import com.inyongtisto.helpers.databinding.FragmentBlankBinding
import com.inyongtisto.helpers.printExample.PrintExampleActivity
import com.inyongtisto.helpers.printExample.PrinterAdapter
import com.inyongtisto.helpers.printExample.PrinterDevice
import com.inyongtisto.helpers.util.BaseActivity
import com.inyongtisto.helpers.util.Rounded
import com.inyongtisto.myhelper.extension.*
import com.inyongtisto.myhelper.printer.Alignment
import com.inyongtisto.myhelper.printer.Size
import com.inyongtisto.myhelper.printer.ThermalPrinter
import com.inyongtisto.myhelper.printer.Style
import java.io.File
import kotlin.concurrent.thread

class BlankActivity : BaseActivity() {

    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentBlankBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}