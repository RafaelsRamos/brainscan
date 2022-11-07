package com.scookie.brainscanner.device.bitalino.utils

import com.scookie.brainscanner.device.bitalino.BitErrorTypes
import com.scookie.brainscanner.device.bitalino.BitException
import java.util.*

object BitValidations {

    private val VALID_SAMPLE_RATES = listOf(1, 10, 100, 1000)
    private val DEFAULT_SAMPLE_RATE = 100

    /**
     * ## Validates the [sampleRate].
     *
     * If an invalid [sampleRate] is provided, [DEFAULT_SAMPLE_RATE] is returned.
     */
    @JvmStatic
    fun validateSampleRate(sampleRate: Int): Int {
        return if (VALID_SAMPLE_RATES.contains(sampleRate)) {
            sampleRate
        } else {
            DEFAULT_SAMPLE_RATE
        }
    }

    /**
     * ## Validate [channels].
     *
     * If invalid channels are given, [BitErrorTypes.INVALID_ANALOG_CHANNELS] is thrown.
     */
    @JvmStatic
    fun validateAnalogChannels(channels: IntArray): IntArray {
        if (channels.isEmpty() || channels.size > 6) {
            throw BitException(BitErrorTypes.INVALID_ANALOG_CHANNELS)
        }

        if (channels.any { channel -> channel !in 0..5 }) {
            throw BitException(BitErrorTypes.INVALID_ANALOG_CHANNELS)
        }

        Arrays.sort(channels)
        return channels
    }

}