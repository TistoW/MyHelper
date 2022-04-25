package com.inyongtisto.helpers.util;

import static java.lang.Math.ceil;

import android.util.Log;

public class Rounded {
    public Rounded(double value, double roundedTo) {
        double roundedTotalAmount = ceil((float) (value / roundedTo)) * roundedTo;
        double roundDownLess = roundedTo * 0.5;
        if (roundedTo + (value - roundedTotalAmount) <= roundDownLess) {
            roundedTotalAmount = ((long) (((long) value) / roundedTo)) * roundedTo;
        }
        double roundedAmout = value - roundedTotalAmount;
        Log.d("TAG", "onCreate: " + value + " - " + roundedTotalAmount + " - " + roundedAmout);
    }
}
