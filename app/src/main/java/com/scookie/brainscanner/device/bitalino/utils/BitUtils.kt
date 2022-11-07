package com.scookie.brainscanner.device.bitalino.utils

import kotlin.math.ceil

object BitUtils {

    /**
     * ## Calculate the bytes, given the number of analog channels in [analogChannels].
     */
    @JvmStatic
    fun calculateTotalBytes(analogChannels: IntArray): Int {
        return if (analogChannels.size <= 4) {
            ceil(((12f + 10f * analogChannels.size) / 8).toDouble()).toInt()
        } else {
            ceil(((52f + 6f * (analogChannels.size - 4)) / 8).toDouble()).toInt()
        }
    }

}