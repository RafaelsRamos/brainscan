package com.scookie.brainscanner.utils.fft

import org.jtransforms.fft.DoubleFFT_1D
import kotlin.math.pow
import kotlin.math.sqrt

private const val SECONDS_IN_MINUTE = 60

internal fun performFFTOn(data: DoubleArray): DoubleArray {
    val fftDo = DoubleFFT_1D(data.size.toLong())
    val fft = DoubleArray(data.size)
    System.arraycopy(data, 0, fft, 0, data.size)
    fftDo.realForward(fft)
    return fft
}

internal fun DoubleArray.calculateRateBetween(minBin: Int, maxBin: Int): Float {
    val fft = this
    val result = DoubleArray(fft.size / 2)

    val size = result.size * 2
    var freqWeightPonderations = 0f
    var sumOfWeights = 0f
    var max = Float.MIN_VALUE.toDouble()

    for (s in minBin until maxBin) {
        val re = fft[s * 2]
        val im = fft[s * 2 + 1]
        result[s] = (sqrt(re * re + im * im).toFloat() / result.size).toDouble() // Doing the module
        result[s] = result[s].pow(2.0)
        freqWeightPonderations += (100f * s.toFloat() / size * result[s]).toFloat()
        sumOfWeights += result[s].toFloat()
        max = kotlin.math.max(max, result[s])
    }
    return freqWeightPonderations / sumOfWeights
}

internal fun calculateBinSizeFor(samplingRate: Int, dataSize: Int): Float {
    val nyquistFreq = samplingRate / 2F
    val numBins = dataSize / 2F
    return nyquistFreq / numBins
}

internal fun calculateBinFor(frequencyPerMinute: Int, binSize: Float): Int {
    val frequency = frequencyPerMinute.toFloat() / SECONDS_IN_MINUTE
    return (frequency / binSize).toInt()
}