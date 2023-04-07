package com.inyongtisto.helpers.printExample

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.*
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.inyongtisto.helpers.databinding.ActivityMainBinding
import com.inyongtisto.helpers.util.BaseActivity
import com.inyongtisto.helpers.util.Rounded
import com.inyongtisto.myhelper.extension.*
import com.inyongtisto.myhelper.printer.Alignment
import com.inyongtisto.myhelper.printer.Size
import com.inyongtisto.myhelper.printer.ThermalPrinter
import com.inyongtisto.myhelper.printer.Style
import kotlin.concurrent.thread

class PrintExampleActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var adapter: PrinterAdapter = PrinterAdapter()
    private var listDevice: MutableList<BluetoothConnection>? = null
    private var printerFunction: ThermalPrinter? = null
    private var bitmapImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter()
        initPrinter()
        getBitmapImage()

        binding.apply {
            btnCheckStatus.setOnClickListener {
                toastSimple("isConnected:" + printerFunction?.isConnected())
            }

            btnPrint.setOnClickListener {
                printText()
            }
        }
    }

    private fun printText() {
        printerFunction?.let {
            progress.show()
            it.addImage(bitmapImage, Alignment.Center, paddingLeft = -3)
            it.addSpace()
            it.addText("Tukang App", Alignment.Center, size = Size.Normal, style = Style.Bold)
            it.addText("Jalan Sederhana No4, Palirangan, Payaman, Kec.Solokuro, Kab.Lamongan", Alignment.Center)
            it.addText("082341810186", Alignment.Center)

            it.addSpace()
            it.addTextColumn(
                it.createText("Tanggal"),
                it.createText("2023-04-20 20:30", Alignment.Right),
            )
            it.addTextColumn(
                it.createText("Kasir"),
                it.createText("Agus Nadhif", Alignment.Right),
            )
            it.addTextColumn(
                it.createText("No.Order"),
                it.createText("A010", Alignment.Right),
            )
            it.addSpace()
            it.addDashLine()
            it.addTextColumn(
                it.createText("1 Martabak"),
                it.createText("10.000", Alignment.Right),
            )
            it.addTextColumn(
                it.createText("1 Es Teh Manis"),
                it.createText("2.000", Alignment.Right),
            )
            it.addDashLine()
            it.addTextColumn(
                it.createText("Subtotal"),
                it.createText("12.000", Alignment.Right),
            )
            it.addTextColumn(
                it.createText("Diskon"),
                it.createText("2.000", Alignment.Right),
            )
            it.addTextColumn(
                it.createText("Pajak(5%)"),
                it.createText("500", Alignment.Right),
            )

            it.addSpace()
            it.addTextColumn(
                it.createText("Total"),
                it.createText("10.500", Alignment.Right),
            )
            it.addTextColumn(
                it.createText("Tunai"),
                it.createText("11.000", Alignment.Right),
            )
            it.addTextColumn(
                it.createText("Kembalian"),
                it.createText("500", Alignment.Right),
            )

            it.addSpace()
            it.addDashLine()
            it.addText("Barang Yang sudang dibeli tidak dapat dikembalikan\nTerima kasih sudah belanja di toko kami", Alignment.Center)

            it.addBarcode("123456")
            it.addSpace()
            it.addQrcode("123456", Alignment.Left)
            it.addSpace()

            longLogs(it.getTextPrint())
            it.print(onPrintError = { message ->
                progress.dismiss()
                toastWarning(message)
            }, onPrintSuccess = {
                progress.dismiss()
            })
        }
    }

    fun getBitmapImage() {
        thread {
            bitmapImage = Glide.with(this)
                    .asBitmap()
                    .load("https://app.zenenta.com/fashion_pria.png")
                    .submit().get()
        }
        delayFunction({
            Log.d("TAG", "bitmapImage: $bitmapImage")
        }, 2000)
    }

    @SuppressLint("SetTextI18n")
    private fun adapter() {
        binding.apply {
            rvSelectPrinter.layoutManager = verticalLayoutManager()
            rvSelectPrinter.adapter = adapter
            adapter.onItemClicked = { printer ->
                connectPrinter(printer)
            }
        }
    }

    private fun initPrinter() {
        requestBluetoothPermissions(requestBluetooth)
        printerFunction = ThermalPrinter(this)
        getBluetoothPrinters()
    }

    @SuppressLint("SetTextI18n")
    private fun connectPrinter(printer: PrinterDevice) {
        progress.show()
        printerFunction?.connectPrinter(printer.address, onErrorConnection = {
            progress.dismiss()
            toastWarning(it)
        }, onConnected = {
            progress.dismiss()
            toastSimple("printer Connected")
            binding.tvStatusPrinter.text = "Connected - ${printer.printerName} - ${printer.address}"
        })
    }

    @SuppressLint("MissingPermission", "NotifyDataSetChanged")
    private fun getBluetoothPrinters() {
        val list = printerFunction?.loadBluetoothPrintersConnections() ?: listOf()
        listDevice?.addAll(list)
        adapter.submitList(list.map { device ->
            PrinterDevice(
                printerName = device.device.name ?: "",
                type = "Bluetooth",
                address = device.device.address ?: ""
            )
        })
        adapter.notifyDataSetChanged()
    }

    private var requestBluetooth = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            logs("All Access Granted")
            getBluetoothPrinters()
        } else {
            dialogExplain()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200) {
            var allGranted = false
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allGranted = true
                } else {
                    allGranted = false
                    break
                }
            }

            if (allGranted) { // generated
                logs("All onRequestPermissionsResult Granted")
                getBluetoothPrinters()
            } else if (shouldShowRequests(android12BluetoothPermission)) {
                getAlertDialog { }
            } else {
                dialogExplain()
            }
        }
    }

}