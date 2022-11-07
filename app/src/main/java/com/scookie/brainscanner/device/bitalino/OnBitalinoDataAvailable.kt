package com.scookie.brainscanner.device.bitalino

import com.scookie.brainscanner.device.bitalino.frame.BitFrame

fun interface OnBitalinoDataAvailable {

    fun onFrameAvailable(bitalinoFrame: BitFrame)

}