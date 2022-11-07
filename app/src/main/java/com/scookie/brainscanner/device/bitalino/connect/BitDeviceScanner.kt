package com.scookie.brainscanner.device.bitalino.connect

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import com.scookie.brainscanner.device.DeviceScanner
import com.scookie.brainscanner.device.bitalino.BitConstants.ACTION_STATE_CHANGED
import com.scookie.brainscanner.device.broadcasts.BluetoothDevicesBroadcastReceiver

class BitDeviceScanner(
    private val context: Context,
    private val onBitDeviceFound: (BluetoothDevice) -> Unit
): DeviceScanner {

    private val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private var bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter

    private val actionFoundIntent = IntentFilter(BluetoothDevice.ACTION_FOUND)
    private val actionPairingRequestIntent = IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST)
    private val receiver = BluetoothDevicesBroadcastReceiver({ isBitDevice() }) { device ->
        onBitDeviceFound.invoke(device)
    }

    init {
        context.registerReceiver(receiver, actionFoundIntent)
        context.registerReceiver(receiver, actionPairingRequestIntent)
    }

    override fun startScanning() {
        // If we're already discovering, STOP it
        if (bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.cancelDiscovery()
        }

        // Request discover from BluetoothAdapter
        bluetoothAdapter.startDiscovery()
    }

    override fun stopScanning() {
        bluetoothAdapter.cancelDiscovery()
    }

    override fun dispose() {
        stopScanning()
        context.unregisterReceiver(receiver)
    }

    override fun fetchPairedDevices(): List<BluetoothDevice> {
        return bluetoothAdapter
            .bondedDevices
            .filter { it.isBitDevice() }
    }

    private fun BluetoothDevice.isBitDevice(): Boolean {
        return if (name == null) {
            // If the device has no name, check by address.
            address.contains("20:15:12")
        } else {
            // If the device has name, check if it contains bitalino.
            name.lowercase().contains("bitalino")
        }
    }
}