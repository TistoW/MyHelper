package com.inyongtisto.myhelper.extension

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.annotation.RequiresApi
import com.inyongtisto.myhelper.databinding.ViewDatePickerBinding
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
fun Activity.dialogDatePicker(
    title: String = "DatePicker",
    date: LocalDateTime = LocalDateTime.now(),
    onSelected: (LocalDateTime) -> Unit
): AlertDialog {
    val binding = ViewDatePickerBinding.inflate(layoutInflater)
    val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
    alertDialog.setView(binding.root)
    alertDialog.setCancelable(true)
    alertDialog.show()
    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    binding.apply {
        var selectedDate: LocalDateTime = date
        tvTitle.text = title
        datePicker.setCurrentDate(selectedDate)
        datePicker.setDatePickerListener {
            selectedDate = it
        }
        btnSimpan.setOnClickListener {
            alertDialog.dismiss()
            onSelected.invoke(selectedDate)
        }
    }
    return alertDialog
}