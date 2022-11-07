package com.scookie.brainscanner.device.bitalino.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class BitState(
    var identifier: String? = null,
    var analogOutput: Int = 0,
    var battery: Int = 0,
    var batThreshold: Int = 0,
    private var analogArray: IntArray = IntArray(6),
    private var digitalArray: IntArray = IntArray(4),
) : Parcelable {

    fun getAnalog(pos: Int): Int {
        return analogArray[pos]
    }

    @Throws(IndexOutOfBoundsException::class)
    fun setAnalog(pos: Int, value: Int) {
        analogArray[pos] = value
    }

    fun getDigital(pos: Int): Int {
        return digitalArray[pos]
    }

    @Throws(IndexOutOfBoundsException::class)
    fun setDigital(pos: Int, value: Int) {
        digitalArray[pos] = value
    }
}