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
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class DateTimePicker(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    var parentView: LinearLayout
    var pickerHour: NumberPicker
    var pickerMinute: NumberPicker
    var pickerFormat: NumberPicker

    enum class DateTimeFormat {
        AM, PM
    }

    private var datePickerListener: ((LocalDateTime) -> Unit)? = null
    private var currentDate = LocalDateTime.now()
    private var currentFormat = currentDate.getFormatTime()

    init {

        // Inflating Layout
        inflate(context, R.layout.date_time_picker, this)

        // Bind View
        parentView = findViewById(R.id.parent_view)
        pickerHour = findViewById(R.id.picker_hour)
        pickerMinute = findViewById(R.id.picker_minute)
        pickerFormat = findViewById(R.id.picker_format)

        // Get Attr
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.DateTimePicker)

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
            pickerHour.selectionDividerHeight = 1.dp
            pickerMinute.selectionDividerHeight = 1.dp
            pickerFormat.selectionDividerHeight = 1.dp
        } else {
            pickerHour.setDividerHeight(1.dp)
            pickerMinute.setDividerHeight(1.dp)
            pickerFormat.setDividerHeight(1.dp)
        }

        // Update Display
        updateHours()
        updateMinutes()
        updateFormats()

        // Set Listener
        pickerHour.setOnValueChangedListener { picker, _, _ ->

            val newDate = currentDate.withHour(picker.value)
            currentDate = newDate

            updateMinutes()
            updateFormats()
            datePickerListener?.invoke(currentDate)
        }

        pickerMinute.setOnValueChangedListener { picker, _, _ ->

            currentDate = currentDate.withMinute(picker.value)

            updateHours()
            updateFormats()
            datePickerListener?.invoke(currentDate)
        }

        pickerFormat.setOnValueChangedListener { picker, _, _ ->

            currentFormat = currentDate.switchFormatTime()
            currentDate = currentDate.switchDateTime(currentFormat)

            updateMinutes()
            updateHours()
            datePickerListener?.invoke(currentDate)
        }

        // Set Dimension
        parentView.layoutParams = params

        attributes.recycle()
    }

    fun setCurrentDate(currentDate: LocalDateTime) {
        this.currentDate = currentDate
        updateHours()
        updateMinutes()
        updateFormats()
    }

    fun setDatePickerListener(datePickerListener: (LocalDateTime) -> Unit) {
        this.datePickerListener = datePickerListener
    }

    private fun updateMinutes() {
        pickerMinute.apply {
            displayedValues = null
            minValue = 0
            maxValue = 0
            value = currentDate.minute
        }
        pickerMinute.apply {
            displayedValues = getMinutes()
            minValue = 0
            maxValue = 59
            value = currentDate.minute
        }
    }

    private fun updateHours() {
        pickerHour.apply {
            displayedValues = null
            minValue = 0
            maxValue = 0
            value = currentDate.hour
        }
        pickerHour.apply {
            displayedValues = getHours()
            minValue = 1
            maxValue = 12
            try {
                value = currentDate.hour
            } catch (e: Exception) {
                value = 12
                e.printStackTrace()
            }
        }
    }

    private fun updateFormats() {
        currentFormat = currentDate.getFormatTime()
        pickerFormat.apply {
            displayedValues = null
            minValue = 0
            maxValue = 0
            value = currentFormat.getFormatTimeValue()
        }
        pickerFormat.apply {
            displayedValues = getFormats()
            minValue = 1
            maxValue = 2
            value = currentFormat.getFormatTimeValue()
        }
    }

    private fun getMinutes(): Array<String> {
        val min = 0
        val max = 60

        val minutes = arrayListOf<String>()

        for (minute in min..max) {
            minutes.add(
                if (minute < 10) {
                    "0$minute"
                } else {
                    minute.toString()
                }
            )
        }

        return minutes.toTypedArray()
    }

    private fun getHours(): Array<String> {
        return arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    }

    private fun getFormats(): Array<String> {
        return arrayOf("AM", "PM")
    }
}