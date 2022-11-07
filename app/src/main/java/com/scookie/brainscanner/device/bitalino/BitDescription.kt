package com.scookie.brainscanner.device.bitalino

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class BitDescription(
    private val isBITalino2: Boolean = false,
    private val fwVersion: Float = 0f,
): Parcelable