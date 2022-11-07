package com.scookie.brainscanner.features.devicesetup.presentation.events

import android.bluetooth.BluetoothDevice

sealed class DeviceListingEvent {

    object StartListening: DeviceListingEvent()

    object StopListening: DeviceListingEvent()

    object ClearAll: DeviceListingEvent()

    data class DeviceSelected(val device: BluetoothDevice): DeviceListingEvent()

}
