package com.scookie.brainscanner.features.devicesetup.domain

import android.bluetooth.BluetoothDevice
import com.scookie.brainscanner.device.DeviceState

data class BitBluetoothDevice(val device: BluetoothDevice, var state: DeviceState)

fun Collection<BitBluetoothDevice>.fetchByAddress(address: String): BitBluetoothDevice? {
    return firstOrNull { it.device.address == address }
}