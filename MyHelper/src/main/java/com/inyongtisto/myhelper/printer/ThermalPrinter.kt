package com.inyongtisto.myhelper.printer

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.graphics.Bitmap
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresPermission
import com.dantsu.escposprinter.connection.DeviceConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.inyongtisto.myhelper.extension.addPaddingLeft
import com.inyongtisto.myhelper.extension.logs
import com.inyongtisto.myhelper.printer.asyc.AsyncBluetoothEscPosPrint
import com.inyongtisto.myhelper.printer.asyc.AsyncEscPosPrint
import com.inyongtisto.myhelper.printer.asyc.AsyncEscPosPrinter

enum class Alignment(val value: String) {
    Center("[C]"),
    Left("[L]"),
    Right("[R]"),
}

enum class Style(val value: String) {
    Bold("b"),
    Underline("u")
}

enum class Size(val value: String) {
    Normal("normal"),
    Wide("wide"),
    Tall("tall"),
    Big1("big"),
    Big2("big-2"),
    Big3("big-3"),
    Big4("big-4"),
    Big5("big-5"),
    Big6("big-6"),
}

enum class BarcodeType(val value: String) {
    EAN13("ean13"),
    EAN8("ean8"),
    UPC_A("upca"),
    UPC_E("upce"),
    Barcode128("128"),
}

class ThermalPrinter(
    private var context: Activity
) {

    private var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    var listBluetoothDevice = ArrayList<BluetoothConnection>()
    private var paperWidth = 50f
    private var charactersPerLine = 32
    private var printerDevice: DeviceConnection? = null
    private var asyncPrinter: AsyncEscPosPrinter? = null
    private var printText = ""
    private var isConnected = false

    init {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        loadBluetoothPrintersConnections()
    }

    fun addText(text: String, alignment: Alignment = Alignment.Left, style: Style? = null, size: Size? = null) {
        val temp = createText(text, alignment, style, size)
        printText += "$temp\n"
    }

    fun addTextColumn(vararg str: String) {
        var text = ""
        str.forEach {
            text += it
        }
        printText += "$text\n"
    }

    fun addSpace(int: Int = 1) {
        for (i in 1..int) {
            addText("")
        }
    }

    fun addDashLine() {
        addText("-------------------------------\n", Alignment.Center)
    }

    fun addImage(bitmap: Bitmap?, alignment: Alignment = Alignment.Center, paddingLeft: Int = 0) {
        val image: String = try {
            "<img>" + PrinterTextParserImg.bitmapToHexadecimalString(asyncPrinter, bitmap?.addPaddingLeft(paddingLeft)) + "</img>"
        } catch (e: Exception) {
            "Image error"
        }
        addText(image, alignment)
    }

    fun addBarcode(barcode: String, alignment: Alignment = Alignment.Center, barcodeType: BarcodeType? = null) {
        val type = barcodeType?.value ?: "128"
        val text = "<barcode type='$type'>$barcode</barcode>"
        addText(text, alignment)
    }

    fun addQrcode(qrcode: String, alignment: Alignment = Alignment.Center, size: Int = 20) {
        val text = "<qrcode size='$size'>$qrcode</qrcode>"
        addText(text, alignment)
    }

    fun createText(text: String, alignment: Alignment = Alignment.Left, style: Style? = null, size: Size? = null): String {
        val styleStart = if (style != null) "<${style.value}>" else ""
        val styleEnd = if (style != null) "</${style.value}>" else ""

        val sizeStart = if (size != null) "<font size='${size.value}'>" else ""
        val sizeEnd = if (size != null) "</font>" else ""

        return "${alignment.value}$styleStart$sizeStart$text$sizeEnd$styleEnd"
    }

    fun loadBluetoothPrintersConnections(): List<BluetoothConnection> {
        val bluetoothDevicesList = BluetoothPrintersConnections().list
        listBluetoothDevice = ArrayList()
        bluetoothDevicesList?.let { listBluetoothDevice.addAll(it) }
        return listBluetoothDevice
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    fun getPairedBluetoothDevice(): List<BluetoothDevice> {
        val deviceList: MutableList<BluetoothDevice> = ArrayList()
        val pairedDevices = BluetoothAdapter.getDefaultAdapter().bondedDevices
        if (pairedDevices.size > 0) {
            val var2: Iterator<BluetoothDevice> = pairedDevices.iterator()
            while (var2.hasNext()) {
                val device = var2.next()
                deviceList.add(device)
            }
        }
        return deviceList
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    fun getSpecificDevice(deviceClass: Int): List<BluetoothDevice> {
        val devices = getPairedBluetoothDevice()
        val printerDevices: MutableList<BluetoothDevice> = ArrayList()
        val var3: Iterator<BluetoothDevice> = devices.iterator()
        while (var3.hasNext()) {
            val device = var3.next()
            val klass = device.bluetoothClass
            if (klass.majorDeviceClass == deviceClass) {
                printerDevices.add(device)
            }
        }
        return printerDevices
    }

    fun getDevice(address: String) {
        val device = listBluetoothDevice.find { it.device.address == address }
    }

    fun isConnected(): Boolean {
        return asyncPrinter?.printerConnection?.isConnected ?: false
    }

    fun setSetupPrinter(paperWidth: Float = this.paperWidth, charactersPerLine: Int = this.charactersPerLine) {
        this.paperWidth = paperWidth
        this.charactersPerLine = charactersPerLine
    }

    fun isBluetoothOn(): Boolean {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled
    }


    fun openBluetoothSetting(launcher: ActivityResultLauncher<Intent>) {
        if (bluetoothAdapter != null) {
            launcher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        } else {
            Toast.makeText(context, "Device Not Support Bluetooth", Toast.LENGTH_SHORT).show()
        }

//    private var bluetoothLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//
//            }
//        }
    }

    fun openBluetoothListSetting(launcher: ActivityResultLauncher<Intent>) {
        launcher.launch(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
//    private var bluetoothLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//
//            }
//        }
    }

    fun connectPrinter(address: String?, onErrorConnection: ((String?) -> Unit)? = null, onConnected: (() -> Unit)? = null) {
        val device = listBluetoothDevice.find { it.device.address == address }
        if (device != null) {
            connectPrinter(device, onErrorConnection, onConnected)
        } else {
            loadBluetoothPrintersConnections()
            val message = "Bluetooth device not found:$address"
            logs(message)
            onErrorConnection?.invoke(message)
        }
    }

    fun connectPrinter(printerConnection: DeviceConnection?, onErrorConnection: ((String?) -> Unit)? = null, onConnected: (() -> Unit)? = null) {
        AsyncBluetoothEscPosPrint(context, object : AsyncEscPosPrint.OnPrintFinished() {

            override fun onError(asyncEscPosPrinter: AsyncEscPosPrinter?, codeException: Int, message: String?) {
                logs("Connect Printer Error:$message")
                isConnected = false
                onErrorConnection?.invoke(message)
            }

            override fun onSuccess(asyncEscPosPrinter: AsyncEscPosPrinter?) {
                logs("Connect Printer success")
                asyncPrinter = asyncEscPosPrinter
                isConnected = asyncEscPosPrinter?.printerConnection?.isConnected ?: false
                onConnected?.invoke()
            }

            override fun onProgressChangeListener(status: Int) {
                logs("connectPrinterOnProgressChangeListener:$status")
            }
        }).execute(getAsyncEscPosPrinter(printerConnection))
    }

    private fun getAsyncEscPosPrinter(printerConnection: DeviceConnection?): AsyncEscPosPrinter {
        printerDevice = printerConnection
        return AsyncEscPosPrinter(printerConnection, 203, paperWidth, charactersPerLine)
    }

    fun getTextPrint(): String {
        return printText
    }

    fun testPrint() {
        print("Test Print Success")
    }

    fun print(
        text: String = printText,
        onPrintError: ((String?) -> Unit)? = null,
        onPrintSuccess: (() -> Unit)? = null,
    ) {
        if (printText.isNotEmpty()) {
            AsyncBluetoothEscPosPrint(context, object : AsyncEscPosPrint.OnPrintFinished() {
                override fun onError(asyncEscPosPrinter: AsyncEscPosPrinter?, codeException: Int, message: String?) {
                    logs("Print Error:$message")
                    onPrintError?.invoke(message)
                }

                override fun onSuccess(asyncEscPosPrinter: AsyncEscPosPrinter?) {
                    logs("Print success")
                    printText = ""
                    asyncPrinter?.textsToPrint = arrayOfNulls(0)
                    onPrintSuccess?.invoke()
                }

                override fun onProgressChangeListener(status: Int) {
                    logs("print onProgressChangeListener:$status")
                }
            }).execute(startEscPosPrinter(text))
        } else {
            logs("Print Error: No text to be printed")
            onPrintError?.invoke("No text to be printed")
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun startEscPosPrinter(text: String): AsyncEscPosPrinter? {
        try {
            if (asyncPrinter == null) asyncPrinter = AsyncEscPosPrinter(printerDevice, 203, paperWidth, charactersPerLine)
        } catch (e: Exception) {
            logs("Print Error:" + e.message)
        }
        return asyncPrinter?.addTextToPrint(text.trimIndent())
    }

}