package com.inyongtisto.myhelper.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import com.inyongtisto.myhelper.R
import com.inyongtisto.myhelper.extension.toGone
import java.time.LocalDateTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class DatePicker(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    var parentView: LinearLayout
    var pickerDay: NumberPicker
    var pickerMonth: NumberPicker
    var pickerYear: NumberPicker

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)

    private var datePickerListener: ((LocalDateTime) -> Unit)? = null
    private var currentDate = LocalDateTime.now()

    init {

        // Inflating Layout
        inflate(context, R.layout.date_picker, this)

        // Bind View
        parentView = findViewById(R.id.parent_view)
        pickerDay = findViewById(R.id.picker_day)
        pickerMonth = findViewById(R.id.picker_month)
        pickerYear = findViewById(R.id.picker_year)

        // Get Attr
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.DatePicker)

        // Get Dimension
        val dimensionWidth = ViewUtils.getOverflowDimension(
            attributes.getLayoutDimension(
                R.styleable.DatePicker_android_layout_width,
                LayoutParams.WRAP_CONTENT
            )
        )
        val dimensionHeight = ViewUtils.getOverflowDimension(
            attributes.getLayoutDimension(
                R.styleable.DatePicker_android_layout_height,
                LayoutParams.WRAP_CONTENT
            )
        )
        val params = LayoutParams(
            dimensionWidth,
            dimensionHeight
        )

        // Set Size Divider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            pickerDay.selectionDividerHeight = 1.dp
            pickerMonth.selectionDividerHeight = 1.dp
            pickerYear.selectionDividerHeight = 1.dp
        } else {
            pickerDay.setDividerHeight(1.dp)
            pickerMonth.setDividerHeight(1.dp)
            pickerYear.setDividerHeight(1.dp)
        }

        // Update Display
        updateDisplayDay()
        updateDisplayMonths()
        updateDisplayYear()

        // Set Listener
        pickerDay.setOnValueChangedListener { picker, _, _ ->

            val newDate = try {
                currentDate.withDayOfMonth(picker.value)
            } catch (e: Exception) {
                e.printStackTrace()
                currentDate.withDayOfMonth(currentDate.month.maxLength())
            }

            currentDate = newDate

            updateDisplayMonths()
            updateDisplayYear()
            datePickerListener?.invoke(currentDate)
        }

        pickerMonth.setOnValueChangedListener { picker, _, _ ->

            currentDate = currentDate.withMonth(picker.value)

            updateDisplayDay()
            updateDisplayYear()
            datePickerListener?.invoke(currentDate)
        }

        pickerYear.setOnValueChangedListener { picker, _, _ ->

            currentDate = currentDate.withYear(picker.value)

            updateDisplayDay()
            updateDisplayMonths()
            datePickerListener?.invoke(currentDate)
        }

        // Set Dimension
        parentView.layoutParams = params

        attributes.recycle()
    }

    fun hideDayPicker() {
        pickerDay.toGone()
    }

    fun setCurrentDate(currentDate: LocalDateTime) {
        this.currentDate = currentDate
        updateDisplayDay()
        updateDisplayMonths()
        updateDisplayYear()
    }

    fun setDatePickerListener(datePickerListener: (LocalDateTime) -> Unit) {
        this.datePickerListener = datePickerListener
    }

    private fun updateDisplayDay() {
        pickerDay.apply {
            displayedValues = null
            minValue = 0
            maxValue = 0
            value = currentDate.dayOfMonth
        }
        pickerDay.apply {
            displayedValues = getDays()
            minValue = 1
            maxValue = currentDate.toLocalDate().lengthOfMonth()
            value = currentDate.dayOfMonth
        }
    }

    private fun updateDisplayMonths() {
        pickerMonth.apply {
            displayedValues = null
            minValue = 0
            maxValue = 0
            value = currentDate.monthValue
        }
        pickerMonth.apply {
            displayedValues = getMonths()
            minValue = 1
            maxValue = 12
            value = currentDate.monthValue
        }
    }

    private fun updateDisplayYear() {
        pickerYear.apply {
            displayedValues = null
            minValue = 0
            maxValue = 0
            value = currentDate.year
        }
        pickerYear.apply {
            displayedValues = getYears()
            minValue = 1999
            maxValue = LocalDateTime.now().year
            value = currentDate.year
        }
    }

    private fun getDays(): Array<String> {
        val min = 1
        val max = currentDate.month.maxLength()

        val days = arrayListOf<String>()

        for (day in min..max) {
            days.add(
                if (day < 10) {
                    "0$day"
                } else {
                    day.toString()
                }
            )
        }

        return days.toTypedArray()
    }

    private fun getMonths(): Array<String> {
        return arrayOf(
            "Januari",
            "Februari",
            "Maret",
            "April",
            "Mei",
            "Juni",
            "Juli",
            "Agustus",
            "September",
            "Oktober",
            "November",
            "Desember"
        )
    }

    private fun getYears(): Array<String> {
        val min = 1999
        val max = year

        val years = arrayListOf<String>()

        for (year in min..max) {
            years.add(year.toString())
        }

        return years.toTypedArray()
    }
}
