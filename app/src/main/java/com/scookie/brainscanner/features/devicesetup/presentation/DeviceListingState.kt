package com.scookie.brainscanner.features.devicesetup.presentation

import com.scookie.brainscanner.features.devicesetup.domain.BitBluetoothDevice

data class DeviceListingState(
    val isScanning: Boolean = false,
    val devicesPaired: List<BitBluetoothDevice> = listOf(),
)
