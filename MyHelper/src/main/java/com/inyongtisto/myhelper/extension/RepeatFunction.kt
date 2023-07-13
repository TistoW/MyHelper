package com.inyongtisto.myhelper.extension

import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

object RepeatFunction {
    var timer = Timer()
    fun start(delay: Int = 1, period: Int = 60, action: TimerTask.() -> Unit) {
        timer = Timer()
        val mDelay = (delay * 1000).toLong() // Delay before the first execution (0 milliseconds)
        val mPeriod = (period * 1000).toLong() // Repeat every 1 minute (60 seconds * 1000 milliseconds)
        timer.scheduleAtFixedRate(mDelay, mPeriod, action)
    }

    fun destroy() {
        timer.cancel()
    }

    fun stop() {
        timer.cancel()
    }

    fun cancel() {
        timer.cancel()
    }
}