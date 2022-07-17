package com.inyongtisto.myhelper.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


fun formatData(formatDate: String = defaultDateFormat, locale: Locale? = Locale.getDefault()): SimpleDateFormat {
    return SimpleDateFormat(formatDate, locale)
}

@SuppressLint("SimpleDateFormat")
fun today(formatDate: String = defaultDateFormat): String {
    val date = Calendar.getInstance().time
    val dateFormat = formatData(formatDate)
    return dateFormat.format(date)
}

fun firstDayOfThisWeek(formatDate: String = defaultDateFormat): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
    val format = formatData(formatDate)
    var date = format.format(calendar.time)
    if (date == lastDayOfThisWeek()) {
        calendar.add(Calendar.DAY_OF_WEEK, -6)
        date = format.format(calendar.time)
    }
    return date
}

fun lastDayOfThisWeek(formatDate: String = defaultDateFormat): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_WEEK, 1)
    return formatData(formatDate).format(calendar.time)
}

fun firstDayOfLastWeek(formatDate: String = defaultDateFormat): String {
    val firstDayOfThisWeek = firstDayOfThisWeek(formatDate)
    val calendar = firstDayOfThisWeek.toCalender(formatDate)
    calendar.add(Calendar.DAY_OF_WEEK, -7)
    return formatData(formatDate).format(calendar.time)
}

fun lastDayOfLastWeek(formatDate: String = defaultDateFormat): String {
    val firstDayOfThisWeek = firstDayOfThisWeek(formatDate)
    val calendar = firstDayOfThisWeek.toCalender(formatDate)
    calendar.add(Calendar.DAY_OF_WEEK, -1)
    return formatData(formatDate).format(calendar.time)
}

fun firstDayOfThisMonth(formatDate: String = defaultDateFormat): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    return formatData(formatDate).format(calendar.time)
}

fun lastDayOfThisMonth(formatDate: String = defaultDateFormat): String {
    val calendar = Calendar.getInstance()
    return formatData(formatDate).format(calendar.toLastDateOfMonth())
}

fun firstDayOfLastMonth(formatDate: String = defaultDateFormat): String {
    val cal = Calendar.getInstance()
    cal.time = lastDayOfLastMonth(formatDate).toDate(formatDate)
    cal[Calendar.DAY_OF_MONTH] = cal.getActualMinimum(Calendar.DAY_OF_MONTH)
    return formatData(formatDate).format(cal.time)
}

fun lastDayOfLastMonth(formatDate: String = defaultDateFormat): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, -1)
    return formatData(formatDate).format(calendar.toLastDateOfMonth())
}

fun last30Day(formatDate: String = defaultDateFormat): String {
    val calendar = Calendar.getInstance()
    return formatData(formatDate).format(calendar.toLastMonth())
}

fun next30Day(formatDate: String = defaultDateFormat): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.MONTH, 1)
    return formatData(formatDate).format(calendar.time)
}

fun last7Day(formatDate: String = defaultDateFormat): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_WEEK, -7)
    return formatData(formatDate).format(calendar.time)
}

fun next7Day(formatDate: String = defaultDateFormat): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_WEEK, 7)
    return formatData(formatDate).format(calendar.time)
}

fun tomorrow(formatDate: String = defaultDateFormat): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_WEEK, 1)
    return formatData(formatDate).format(calendar.time)
}

fun yesterday(formatDate: String = defaultDateFormat): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_WEEK, -1)
    return formatData(formatDate).format(calendar.time)
}

fun nextDay(day: Int = 1, formatDate: String = defaultDateFormat): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_WEEK, day)
    return formatData(formatDate).format(calendar.time)
}

fun String.toCalender(formatDate: String = defaultDateFormat): Calendar {
    val sdf = formatData(formatDate)
    val date = sdf.parse(this)
    return date?.toCalendar() ?: Calendar.getInstance()
}

fun String.toDate(formatDate: String = defaultDateFormat): Date {
    return formatData(formatDate).parse(this) ?: Calendar.getInstance().toDate()
}

fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
}

fun Calendar.toDate(): Date {
    return this.time
}

fun Calendar.toLastMonth(): Date {
    add(Calendar.MONTH, -1)
    return time
}

fun Calendar.toLastDateOfMonth(): Date {
    add(Calendar.MONTH, 1)
    set(Calendar.DAY_OF_MONTH, 1)
    add(Calendar.DATE, -1)
    return time
}

fun Calendar.toNextWeek(): Date {
    add(Calendar.DAY_OF_WEEK, 6)
    return time
}

fun Calendar.toLastWeek(): Date {
    add(Calendar.DAY_OF_WEEK, -6)
    return time
}