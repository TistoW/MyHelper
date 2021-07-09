package com.inyongtisto.myhelper;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class OnEditTextChanged implements View.OnClickListener {

    private OnFocusChangedListener mOnFocusChanged;

    public OnEditTextChanged(EditText editText, @Nullable OnRefreshListener listener) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                assert listener != null;
                listener.onChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (mOnFocusChanged != null) {
                mOnFocusChanged.onChanged(hasFocus);
            }
        });
    }

    public OnEditTextChanged setOnFocusChanged(@Nullable OnFocusChangedListener listener) {
        mOnFocusChanged = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
    }

    public interface OnRefreshListener {
        void onChanged(String s);
    }

    public interface OnFocusChangedListener {
        void onChanged(Boolean b);
    }
}
