package com.scookie.brainscanner.device.bitalino

class BitException(errorType: BitErrorTypes) : Exception(errorType.description) {

    private val code: Int = errorType.value

}