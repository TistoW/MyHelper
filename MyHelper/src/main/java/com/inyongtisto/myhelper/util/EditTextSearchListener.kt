package com.inyongtisto.myhelper.util

import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import android.view.View
import android.view.View.OnFocusChangeListener

class EditTextSearchListener(
    editText: EditText,
    val delay: Long = 1000,
    private val onChange: ((s: String) -> Unit?)? = null
) {
    var lastTextEdit: Long = 0
    var handler = Handler(Looper.myLooper()!!)
    var text = ""

    init {
        listener(editText)
    }

    private fun listener(editText: EditText) {
        editText.onFocusChangeListener = OnFocusChangeListener { _: View?, _: Boolean ->

        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //You need to remove this to run only once
                text = s.toString()
                handler.removeCallbacks(inputFinishChecker)
            }

            override fun afterTextChanged(s: Editable) {
                //avoid triggering event when text is empty
                if (s.isNotEmpty()) {
                    lastTextEdit = System.currentTimeMillis()
                    handler.postDelayed(inputFinishChecker, delay)
                } else onClear?.invoke(s.toString())
            }
        })
    }

    private val inputFinishChecker = Runnable {
        if (System.currentTimeMillis() > lastTextEdit + delay - 500) {
            onChange?.invoke(text)
        }
    }

    var onFocus: (() -> Unit?)? = null
    var onClear: ((s: String) -> Unit?)? = null
}