package com.scookie.brainscanner.device.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.scookie.brainscanner.device.DeviceState
import com.scookie.brainscanner.device.bitalino.BitConstants

class DeviceStateBroadcastReceiver(
    private val onStateChanged: (String, DeviceState) -> Unit
): BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            BitConstants.ACTION_STATE_CHANGED -> onDeviceStateChanged(intent)
        }
    }

    private fun onDeviceStateChanged(intent: Intent) {
        val deviceId = intent.getStringExtra(BitConstants.IDENTIFIER) ?: return

        val deviceStateId = intent.getIntExtra(BitConstants.EXTRA_STATE_CHANGED, -1)
        val state = DeviceState.getStateById(deviceStateId)

        onStateChanged.invoke(deviceId, state)
    }

}