package com.inyongtisto.helpers.printExample

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrinterDevice(
    val printerName: String = "",
    val type: String = "",
    val address: String = "",
    val selected: Boolean = false,
) : Parcelable