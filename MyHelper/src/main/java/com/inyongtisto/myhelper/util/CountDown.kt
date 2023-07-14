package com.inyongtisto.myhelper.util
import java.util.*

class CountDown(
    var interval: Int = 10,
    var timeFormat: Boolean = false,
    var onDone: (() -> Unit)? = null,
    var countDown: ((String) -> Unit)? = null
) {
    private var startInterval = 0
    private var pauseAt = 0
    private var timer: Timer? = null
    private var isPause = false

    init {
        startInterval = interval
    }

    fun start(interval: Int = this.interval) {
        startInterval = interval
        this.interval = interval
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val second = setInterval()
                var text = second.toString()
                if (timeFormat) {
                    val (minutes, remainingSeconds) = convertSecondsToMinutesAndSeconds(second)
                    text = "${minutes.timePadding()}:${remainingSeconds.timePadding()}"
                }
                countDown?.invoke(text)
                if (second == 0) {
                    this@CountDown.interval = startInterval
                    onDone?.invoke()
                }
            }
        }, 1000L, 1000L)
    }

    private fun Int.timePadding() = this.toString().padStart(2, '0')

    private fun convertSecondsToMinutesAndSeconds(seconds: Int): Pair<Int, Int> {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return Pair(minutes, remainingSeconds)
    }

    private fun setInterval(): Int {
        if (interval == 1) {
            timer?.cancel()
        }
        return --interval
    }

    fun stop() {
        timer?.cancel()
    }

    fun pause() {
        isPause = true
        pauseAt = interval
        stop()
    }

    fun resume() {
        if (isPause) {
            start(startInterval)
            interval = pauseAt
        }
    }

    fun reset() {
        stop()
        start(startInterval)
    }
}