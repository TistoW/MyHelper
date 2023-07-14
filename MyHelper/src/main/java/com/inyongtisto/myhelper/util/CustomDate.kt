package com.inyongtisto.myhelper.util

import com.inyongtisto.myhelper.extension.*

object CustomDate {
    fun getToday(formatDate: String = defaultDateFormat) = today(formatDate)
    fun getFirstDayOfThisWeek(formatDate: String = defaultDateFormat) = firstDayOfThisWeek(formatDate)
    fun getLastDayOfThisWeek(formatDate: String = defaultDateFormat) = lastDayOfThisWeek(formatDate)
    fun getFirstDayOfLastWeek(formatDate: String = defaultDateFormat) = firstDayOfLastWeek(formatDate)
    fun getLastDayOfLastWeek(formatDate: String = defaultDateFormat) = lastDayOfLastWeek(formatDate)

    fun getFirstDayOfThisMonth(formatDate: String = defaultDateFormat) = firstDayOfThisMonth(formatDate)
    fun getLastDayOfThisMonth(formatDate: String = defaultDateFormat) = lastDayOfThisMonth(formatDate)
    fun getFirstDayOfLastMonth(formatDate: String = defaultDateFormat) = firstDayOfLastMonth(formatDate)
    fun getLastDayOfLastMonth(formatDate: String = defaultDateFormat) = lastDayOfLastMonth(formatDate)

    fun getLast30Day(formatDate: String = defaultDateFormat) = last30Day(formatDate)
    fun getNext30Day(formatDate: String = defaultDateFormat) = next30Day(formatDate)
    fun getLast7Day(formatDate: String = defaultDateFormat) = last7Day(formatDate)
    fun getNext7Day(formatDate: String = defaultDateFormat) = next7Day(formatDate)

    fun getTomorrow(formatDate: String = defaultDateFormat) = tomorrow(formatDate)
    fun getYesterday(formatDate: String = defaultDateFormat) = yesterday(formatDate)

    fun getNextDay(day: Int = 1, formatDate: String = defaultDateFormat) = nextDay(day, formatDate)


}