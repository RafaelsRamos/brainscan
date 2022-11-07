package com.scookie.brainscanner.device.bitalino.frame

import kotlinx.parcelize.IgnoredOnParcel

data class BitFrame(
    var identifier: String? = null,
    var sequence: Int = 0,
    private val buffer: ByteArray? = null,
    private val totalBytes: Int = 0
) {

    @IgnoredOnParcel
    val analogArray: IntArray = buffer?.let {
        intArrayOf(
            buffer[totalBytes - 2].toInt() and 0xF shl 6 or (buffer[totalBytes - 3].toInt() and 0XFC shr 2) and 0x3ff,
            buffer[totalBytes - 3].toInt() and 0x3 shl 8 or (buffer[totalBytes - 4].toInt() and 0xff) and 0x3ff,
            buffer[totalBytes - 5].toInt() and 0xff shl 2 or (buffer[totalBytes - 6].toInt() and 0xc0 shr 6) and 0x3ff,
            buffer[totalBytes - 6].toInt() and 0x3F shl 4 or (buffer[totalBytes - 7].toInt() and 0xf0 shr 4) and 0x3ff,
            buffer[totalBytes - 7].toInt() and 0x0F shl 2 or (buffer[totalBytes - 8].toInt() and 0xc0 shr 6) and 0x3f,
            buffer[totalBytes - 8].toInt() and 0x3F,
        )
    } ?: intArrayOf()

    @IgnoredOnParcel
    val digitalArray: IntArray = buffer?.let {
        intArrayOf(
            buffer[totalBytes - 2].toInt() shr 7 and 0x01,
            buffer[totalBytes - 2].toInt() shr 6 and 0x01,
            buffer[totalBytes - 2].toInt() shr 5 and 0x01,
            buffer[totalBytes - 2].toInt() shr 4 and 0x01,
        )
    } ?: intArrayOf()

    fun setAnalog(pos: Int, value: Int) {
        analogArray[pos] = value
    }

    fun setDigital(pos: Int, value: Int) {
        digitalArray[pos] = value
    }

    fun updateAnalogValues(buffer: ByteArray, totalBytes: Int) {
        val j = totalBytes - 1

        analogArray[0] = buffer[j - 1].toInt() and 0xF shl 6 or (buffer[j - 2].toInt() and 0XFC shr 2) and 0x3ff
        analogArray[1] = buffer[j - 2].toInt() and 0x3 shl 8 or (buffer[j - 3].toInt() and 0xff) and 0x3ff
        analogArray[2] = buffer[j - 4].toInt() and 0xff shl 2 or (buffer[j - 5].toInt() and 0xc0 shr 6) and 0x3ff
        analogArray[3] = buffer[j - 5].toInt() and 0x3F shl 4 or (buffer[j - 6].toInt() and 0xf0 shr 4) and 0x3ff
        analogArray[4] = buffer[j - 6].toInt() and 0x0F shl 2 or (buffer[j - 7].toInt() and 0xc0 shr 6) and 0x3f
        analogArray[5] = buffer[j - 7].toInt() and 0x3F
    }

    fun updateDigitalValues(buffer: ByteArray, totalBytes: Int) {
        val j = totalBytes - 1

        digitalArray[0] = buffer[j - 1].toInt() shr 7 and 0x01
        digitalArray[1] = buffer[j - 1].toInt() shr 6 and 0x01
        digitalArray[2] = buffer[j - 1].toInt() shr 5 and 0x01
        digitalArray[3] = buffer[j - 1].toInt() shr 4 and 0x01
    }

}