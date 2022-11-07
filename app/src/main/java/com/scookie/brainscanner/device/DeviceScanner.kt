package com.scookie.brainscanner.device

import android.bluetooth.BluetoothDevice


interface DeviceScanner {

    fun startScanning()

    fun stopScanning()

    fun dispose()

    fun fetchPairedDevices(): List<BluetoothDevice>

}