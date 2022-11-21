package com.scookie.brainscanner.utils.fft

import com.scookie.brainscanner.physiology.PhysiologicConstants.MAX_BREATHS_PER_MINUTE
import com.scookie.brainscanner.physiology.PhysiologicConstants.MAX_HEART_BEATS_PER_MINUTE
import com.scookie.brainscanner.physiology.PhysiologicConstants.MIN_BREATHS_PER_MINUTE
import com.scookie.brainscanner.physiology.PhysiologicConstants.MIN_HEART_BEATS_PER_MINUTE
import com.scookie.brainscanner.physiology.models.FFTWaves

internal object FFT {

    private const val SAMPLING_RATE = 100

    fun processEEGData(data: DoubleArray): FFTWaves {
        val fft = performFFTOn(data)

        return FFTWaves(
            delta = fft.calculateRateBetween(1, 3),
            theta = fft.calculateRateBetween(4, 7),
            alpha = fft.calculateRateBetween(8, 12),
            lowBeta = fft.calculateRateBetween(13, 16),
            midBeta = fft.calculateRateBetween(17, 20),
            highBeta = fft.calculateRateBetween(21, 30),
            gamma = fft.calculateRateBetween(31, 42),
        )
    }

    fun calculateBreathingRate(data: DoubleArray): Float {
        val fft = performFFTOn(data)

        val binSize = calculateBinSizeFor(
            samplingRate = SAMPLING_RATE,
            dataSize = fft.size
        )

        val minBreathCap = calculateBinFor(MIN_BREATHS_PER_MINUTE, binSize) // 6 breaths per minute
        val maxBreathCap = calculateBinFor(MAX_BREATHS_PER_MINUTE, binSize) // 30 breaths per minute

        return fft.calculateRateBetween(minBreathCap, maxBreathCap)
    }

    fun calculateHeartRate(data: DoubleArray): Float {
        val fft = performFFTOn(data)

        val binSize = calculateBinSizeFor(
            samplingRate = SAMPLING_RATE,
            dataSize = fft.size
        )

        val minHRCap = calculateBinFor(MIN_HEART_BEATS_PER_MINUTE, binSize) // 50 beatings per minute
        val maxHRCap = calculateBinFor(MAX_HEART_BEATS_PER_MINUTE, binSize) // 180 beatings per minute

        return fft.calculateRateBetween(minHRCap, maxHRCap)
    }
}