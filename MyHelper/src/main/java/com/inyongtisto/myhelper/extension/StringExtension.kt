package com.inyongtisto.myhelper.extension

import java.text.NumberFormat
import java.util.*

fun String.remove(string: String): String = replace(string, "")

fun String.removeComma(): String = replace(",", "")

fun String.fixPhoneNumber(): String {
    return when {
        take(1) == "0" -> "62${this.substring(1, this.length)}"
        take(2) == "62" -> this
        isNullOrEmpty() -> this
        else -> "62$this"
    }
}

fun Int.convertRupiah(hideCurrency: Boolean = false): String {
    val localeID = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(localeID)
    var value = format.format(this).replace(",00", "")
    if (hideCurrency) value = value.replace("Rp", "")
    return value
}

fun Double.convertRupiah(hideCurrency: Boolean = false): String {
    val localeID = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(localeID)
    var value = format.format(this).replace(",00", "")
    if (hideCurrency) value = value.replace("Rp", "")
    return value
}

fun String?.convertRupiah(hideCurrency: Boolean = false): String {
    if (this == null || this.isEmpty()) return ""
    val localeID = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(localeID)
    var value = format.format(this.toDouble()).replace(",00", "")
    if (hideCurrency) value = value.replace("Rp", "")
    return value
}

fun String.getYoutubeId(): String {
    return when {
        this.contains("youtu.be") -> this.split("/")[3]
        this.contains("youtube.com") -> this.substringAfter("watch?v=").substringBefore("&")
        else -> {
            this
        }
    }
}