package com.inyongtisto.myhelper.extension

import android.annotation.SuppressLint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

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
    toFormat: String,
    fromFormat: String = "yyyy-MM-dd kk:mm:ss"
): String {
    val dateFormat = SimpleDateFormat(fromFormat)
    val convert = dateFormat.parse(this)
    dateFormat.applyPattern(toFormat)
    return dateFormat.format(convert ?: "")
}


const val defaultDateFormat = "yyyy-MM-dd kk:mm:ss"
const val defaultUTCDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

@SuppressLint("SimpleDateFormat")
fun String.convertToUTC(
    fromatLama: String = defaultDateFormat
): String {
    val formatBaru = defaultUTCDateFormat
    val dateFormat = SimpleDateFormat(fromatLama)
    val confert = dateFormat.parse(this)
    dateFormat.applyPattern(formatBaru)
    return dateFormat.format(confert ?: "")
}

@SuppressLint("SimpleDateFormat")
fun String.convertFromUTC(ygDimau: String = defaultDateFormat): String {
    var hasil = ""
    val frmtlama = defaultUTCDateFormat
    val dateFormat = SimpleDateFormat(frmtlama)
    try {
        val dd = dateFormat.parse(this)
        // tambah 7 jam untuk indonesia
        val hour: Long = 3600 * 1000 // in milli-seconds.
        val newDate = Date(dd!!.time + 7 * hour)
        dateFormat.applyPattern(ygDimau)
        hasil = dateFormat.format(newDate)

    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return hasil
}

fun String.convertFromUTCDay(): String {
    return this.convertFromUTCDayTime(false)
}

@SuppressLint("SimpleDateFormat")
fun String.convertFromUTCDayTime(time: Boolean = true): String {
    val date = this.convertFromUTC()

    var tanggal = ""
    var hari = ""

    val formatTgl = "dd MMM yyyy${if (time) " kk:mm" else ""}"
    val formatHari = "EEEE"
    val formatLama = defaultDateFormat

    val dateFormat = SimpleDateFormat(formatLama)
    val dateFormat2 = SimpleDateFormat(formatLama)
    try {
        val dd = dateFormat.parse(date)
        dateFormat.applyPattern(formatTgl)
        tanggal = dateFormat.format(dd!!)

        val mHari = dateFormat2.parse(date)
        dateFormat2.applyPattern(formatHari)
        hari = dateFormat2.format(mHari!!)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    when (hari) {
        "Sunday" -> hari = "Minggu"
        "Monday" -> hari = "Senin"
        "Tuesday" -> hari = "Selasa"
        "Wednesday" -> hari = "Rabo"
        "Thursday" -> hari = "Kamis"
        "Friday" -> hari = "Jumat"
        "Saturday" -> hari = "Sabtu"
    }

    return "$hari, $tanggal${if (time) " WIB" else ""}"
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

fun String.searchQuery(): String {
    return "%$this%"
}

fun getRandomName(withNumber: Boolean = false): String {
    val listName = listOf(
        "Jude", "Isabella", "Arthur", "Millie", "Andrea", "Marcus", "Atlas", "Ariella", "Kyle", "Evan", "Ira", "Hayden",
        "Bailey", "Gianna", "Valerie", "Brianna", "Jesse", "Cecilia", "Leo", "Leilani", "Dante", "Zoe", "Khadijah", "Mya", "Sharon",
        "Sean", "Brielle", "Ayla", "Shia", "Riley", "Raya", "Sloane", "Alana", "Charlie", "Kian", "Hudson", "Elise", "Akira", "Mika", "Freya",
        "Nia", "Natasha", "Myra", "Mateo", "Everett", "Rae", "Savannah", "Thea", "Finley", "Alaina", "Mina", "Yara", "Emerson", "Camille", "Ivan", "Skyler",
        "Skylar", "Alma", "Reese", "Sasha", "Asa", "Sage", "Camila", "Amira", "Kieran", "Monica", "Everly", "Evie", "Maverick", "Kyra", "Ian", "Julia", "Vivian",
        "Theo", "Ophelia", "Chelsea", "Azariah", "Jade", "Lara", "Ava", "Morgan", "Lennox", "Luna", "Isabelle", "Amir", "Rhys", "Arlo", "Giovanni", "Aisha", "Orion",
        "Ahmed", "Nolan", "Ezekiel", "Michelle", "Lea", "Silas", "Elaine", "Molly",
    )
    val rndNumber = Random.nextInt(11, 99)
    val rnd = Random.nextInt(0, listName.size - 1)
    return listName[rnd] + "" + if (withNumber) rndNumber else ""
}

fun generateRandomName(withNumber: Boolean = false) = getRandomName(withNumber)

@SuppressLint("SimpleDateFormat")
fun getSalam(): String {
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



