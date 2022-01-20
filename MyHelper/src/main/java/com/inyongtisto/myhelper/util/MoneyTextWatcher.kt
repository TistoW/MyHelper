package com.inyongtisto.myhelper.util

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.logs
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class MoneyTextWatcher(
    private val editText: AppCompatEditText,
    private val onChange: ((s: String) -> Unit)? = null
) : TextWatcher {

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(s: Editable) {
        editText.removeTextChangedListener(this)
        try {
            var originalString = s.toString()
            if (originalString.contains(",")) {
                originalString = originalString.replace(",".toRegex(), "")
            }
            if (originalString == "") {
                editText.setText(originalString)
            } else {
                val longVal = originalString.toLong()
                val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
                formatter.applyPattern("#,###,###,###")
                val formattedString = formatter.format(longVal)

                //setting text after format to EditText
                editText.setText(formattedString)
                editText.setSelection(editText.text!!.length)
            }
        } catch (nfe: NumberFormatException) {
            nfe.printStackTrace()
        }

        onChange?.invoke(editText.getString())
        editText.addTextChangedListener(this)
    }
}