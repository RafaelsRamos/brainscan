package com.scookie.brainscanner.physiology.models

/**
 * Result of a Fast Fourier Transform.
 * Brain bins:
 * @param delta from 1 Hz to 3 Hz
 * @param theta from 4 Hz to 7 Hz
 * @param alpha from 8 Hz to 12 Hz
 * @param lowBeta from 13 Hz to 16 Hz
 * @param midBeta from 17 Hz to 20 Hz
 * @param highBeta from 21 Hz to 30 Hz
 * @param gamma from 31 Hz to 42 Hz
 */
data class FFTWaves(
    val delta: Float = Float.MIN_VALUE,
    val theta: Float = Float.MIN_VALUE,
    val alpha: Float = Float.MIN_VALUE,
    val lowBeta: Float = Float.MIN_VALUE,
    val midBeta: Float = Float.MIN_VALUE,
    val highBeta: Float = Float.MIN_VALUE,
    val gamma: Float = Float.MIN_VALUE
) {
    val beta get() = lowBeta + midBeta + highBeta
}