package com.scookie.brainscanner.device.broadcasts

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.scookie.brainscanner.device.DeviceState
import com.scookie.brainscanner.device.bitalino.BitConstants.ACTION_STATE_CHANGED
import com.scookie.brainscanner.device.bitalino.BitConstants.EXTRA_STATE_CHANGED
import com.scookie.brainscanner.device.bitalino.BitConstants.IDENTIFIER
import timber.log.Timber

class BluetoothDevicesBroadcastReceiver(
    val deviceSelector: BluetoothDevice.() -> Boolean,
    val onDeviceFound: (BluetoothDevice) -> Unit
): BroadcastReceiver() {

    companion object {

        const val ACTION_MESSAGE_SCAN = "ACTION_MESSAGE_SCAN"
        const val EXTRA_DEVICE_SCAN = "EXTRA_DEVICE_SCAN"
        const val EXTRA_DEVICE_RSSI = "EXTRA_DEVICE_RSSI"

        const val PAIR_PIN = "1234"

    }

    private val Intent.fetchBitDevice: BluetoothDevice? get() {
        val device = getParcelableExtra<BluetoothDevice>(EXTRA_DEVICE)
        return if (device?.deviceSelector() == true) device else null
    }

    override fun onReceive(context: Context, intent: Intent) {

        when (intent.action) {

            ACTION_FOUND           -> onNewDeviceFound(intent, context)
            ACTION_PAIRING_REQUEST -> onDevicePairingAttempted(intent)

        }

    }

    private fun onNewDeviceFound(intent: Intent, context: Context) {
        val device = intent.fetchBitDevice ?: return
        val rssi = intent.getShortExtra(EXTRA_RSSI, Short.MIN_VALUE).toInt()

        Timber.tag("DEVICE").i("device found: ${device.name} - ${device.address}")

        onDeviceFound.invoke(device)
        val scanIntent = Intent(ACTION_MESSAGE_SCAN).apply {
            putExtra(EXTRA_DEVICE_SCAN, device)
            putExtra(EXTRA_DEVICE_RSSI, rssi)
        }
        context.sendBroadcast(scanIntent)
    }

    private fun onDevicePairingAttempted(intent: Intent) {
        val device = intent.fetchBitDevice ?: return

        Timber.tag("DEVICE").i("Trying to pair to device ${device.name} (${device.address})")
        val type = intent.getIntExtra(EXTRA_PAIRING_VARIANT, ERROR)
        if (type == PAIRING_VARIANT_PIN) {
            device.setPin(PAIR_PIN.toByteArray())
            abortBroadcast()
        }
    }

}