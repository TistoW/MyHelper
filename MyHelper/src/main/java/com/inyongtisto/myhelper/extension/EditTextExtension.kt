package com.inyongtisto.myhelper.extension

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.inyongtisto.myhelper.R
import com.inyongtisto.myhelper.util.MoneyTextWatcher
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern

fun EditText.getString(): String {
    return this.text.toString()
}

fun EditText.isValidPassword(): Boolean {
    val passwordREGEX = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$"
    );
    return passwordREGEX.matcher(this.text.toString()).matches()
}

fun EditText.setEmptyError() {
    this.error = context.getString(R.string.kolom_tidak_boleh_kosong)
    this.requestFocus()
}

fun EditText.isEmpty(setError: Boolean = true): Boolean {
    return if (this.text.isEmpty()) {
        if (setError) {
            this.error = context.getString(R.string.kolom_tidak_boleh_kosong)
            this.requestFocus()
        }
        true
    } else false
}

fun EditText.clearText() {
    setText("")
    clearFocus()
}

fun AppCompatEditText.clearSearch() {
    setText("")
    clearFocus()
}

fun AppCompatEditText.addCustomTextWatcher(button: View) {

    context.getColor(android.R.color.holo_blue_bright)
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            button.isEnabled = !s.isNullOrEmpty()
        }
    })
}

fun EditText.onChangeRupiah(onChange: ((s: String) -> Unit?)? = null) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            lenghtBefor = p0!!.length
        }

        private var lenghtBefor = 0
        private var current = ""
        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (s.toString() != current) {
                val v = s.toString().replace(",00", "")
                var cleanString = v.replace("[Rp,.]".toRegex(), "")
                val localeID = Locale("in", "ID")
                if (cleanString.isEmpty()) {
                    cleanString = "0"
                }
                val parsed = java.lang.Double.parseDouble(cleanString)
                val formatted = NumberFormat.getCurrencyInstance(localeID).format(parsed)

                val nilai = formatted.replace("Rp", "").replace(",00", "")
                if (formatted.length < 18) {
                    current = nilai
                    onChange?.invoke(cleanString)
                    this@onChangeRupiah.setText(nilai)
                    this@onChangeRupiah.setSelection(current.length)
                } else {
                    this@onChangeRupiah.setText(current)
                    this@onChangeRupiah.setSelection(current.length)
                }
            }
        }
    })
}

fun TextInputEditText.onChangeListener(onChange: ((s: String) -> Unit)? = null) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onChange?.invoke(s.toString())
        }
    })
}

fun EditText.onChangeListener(onChange: ((s: String) -> Unit?)? = null) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onChange?.invoke(s.toString())
        }
    })
}

fun AppCompatEditText.addRupiahListener(onChange: ((s: String) -> Unit)? = null) {
    addTextChangedListener(MoneyTextWatcher(this) { onChange?.invoke(it) })
}

fun TextInputEditText.addRupiahListener(onChange: ((s: String) -> Unit)? = null) {
    addTextChangedListener(MoneyTextWatcher(this) { onChange?.invoke(it) })
}

fun TextInputEditText.addFirstTextCapitalListener(onChange: ((s: String) -> Unit)? = null) {
    addTextChangedListener(FirstCapitalTextWatcher(this) { onChange?.invoke(it) })
}

class FirstCapitalTextWatcher(
    private val editText: AppCompatEditText,
    private val onChange: ((s: String) -> Unit)? = null
) : TextWatcher {

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(s: Editable) {
        editText.removeTextChangedListener(this)
        try {
            var originalString = s.toString()
            if (originalString.length == 1) {
                originalString = originalString.uppercase()
            }
            //setting text after format to EditText
            editText.setText(originalString)
            editText.setSelection(editText.text!!.length)
        } catch (nfe: NumberFormatException) {
            nfe.printStackTrace()
        }

        onChange?.invoke(editText.getString())
        editText.addTextChangedListener(this)
    }
}

fun AppCompatEditText.setOnSearchActionListener(onSearch: (s: String) -> Unit) {
    setOnEditorActionListener(object : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearch.invoke(this@setOnSearchActionListener.text.toString())
                return true
            }
            return false
        }
    })
}

fun TextInputLayout.setStartDrawable(drawable: Int) {
//    this.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, drawable), null)
    this.setStartIconDrawable(drawable)
//    this.setStartIconMode(TextInputLayout.END_ICON_CUSTOM)
}


