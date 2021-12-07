package com.inyongtisto.myhelper.extension

import android.annotation.SuppressLint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.NumberFormat
import java.text.SimpleDateFormat
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

fun String?.uppercaseFirstChar(): String {
    return this?.replaceFirstChar { it.uppercaseChar() } ?: ""
}

@SuppressLint("SimpleDateFormat")
fun String.convertTanggal(
    tgl: String,
    formatBaru: String,
    fromatLama: String = "yyyy-MM-dd kk:mm:ss"
): String {
    val dateFormat = SimpleDateFormat(fromatLama)
    val confert = dateFormat.parse(tgl)
    dateFormat.applyPattern(formatBaru)
    return dateFormat.format(confert ?: "")
}

fun Int.toRupiah(hideCurrency: Boolean = false): String {
    val localeID = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(localeID)
    var value = format.format(this).replace(",00", "")
    if (hideCurrency) value = value.replace("Rp", "")
    return value
}


fun Int?.toRupiah(hideCurrency: Boolean = false): String {
    return (this ?: 0).toRupiah(hideCurrency)
}

fun Double.toRupiah(hideCurrency: Boolean = false): String {
    val localeID = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(localeID)
    var value = format.format(this).replace(",00", "")
    if (hideCurrency) value = value.replace("Rp", "")
    return value
}

fun String?.toRupiah(hideCurrency: Boolean = false): String {
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

fun String.toRequestBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}

fun Int.toRequestBody(): RequestBody {
    return this.toString().toRequestBody("text/plain".toMediaTypeOrNull())
}


@SuppressLint("SimpleDateFormat")
fun String.toSalam(): String {
    val dateNow = System.currentTimeMillis()
    val sTgl = SimpleDateFormat("dd MMMM yyyy")
    val sJam = SimpleDateFormat("kk")
    val tgl: String = sTgl.format(dateNow)
    val jam: String = sJam.format(dateNow)

    val iJam = jam.toInt()
    var salam = ""
    if (iJam <= 10) salam = "Selamat Pagi"
    if (iJam in 11..14) salam = "Selamat Siang"
    if (iJam in 13..18) salam = "Selamat Sore"
    if (iJam in 19..24) salam = "Selamat Malam"
    return salam
}

fun String?.getInitial(): String {
    if (this.isNullOrEmpty()) return ""
    val array = this.split(" ")
    if (array.isEmpty()) return this
    var inisial = array[0].substring(0, 1)
    if (array.size > 1) inisial += array[1].substring(0, 1)
    return inisial.uppercase()
}

fun String?.toKFormat(): String {
    if (this == null) return ""
    return if (this.length > 4) {
        return when (this.length) {
            4 -> this.toRupiah(true).dropLast(2) + "K"
            in 5..6 -> this.dropLast(3) + "K"
            7 -> this.toRupiah(true).dropLast(6) + "M"
            in 8..9 -> this.dropLast(6) + "M"
            10 -> this.toRupiah(true).dropLast(6) + "M"
            in 8..9 -> this.dropLast(10) + "B"
            in 11..100 -> this.dropLast(9).toRupiah(true) + "B"
            else -> this
        }
    } else this
}



