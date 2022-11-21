package com.scookie.brainscanner.device.bitalino

import androidx.annotation.FloatRange

data class SignalQuality(
    @FloatRange(0.0, 1.0)
    val rightEEG: Float = 0F,
    @FloatRange(0.0, 1.0)
    val leftEEG: Float = 0F,
) {

    companion object {

        private const val NOISE_THRESHOLD = 1000

        fun calculateFor(
            rightEEGData: List<Int>,
            leftEEGData: List<Int>
        ): SignalQuality {
            val rightValuesBelowThreshold = rightEEGData.count { value -> value < NOISE_THRESHOLD }
            val leftValuesBelowThreshold = leftEEGData.count { value -> value < NOISE_THRESHOLD }
            return SignalQuality(
                rightEEG = rightValuesBelowThreshold.toFloat() / rightEEGData.count(),
                leftEEG = leftValuesBelowThreshold.toFloat() / leftEEGData.count(),
            )
        }

    }

}
