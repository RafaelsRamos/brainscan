package com.scookie.brainscanner.features.devicesetup.presentation

import android.app.Application
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.postDelayed
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.scookie.brainscanner.device.DeviceHolder
import com.scookie.brainscanner.device.DeviceScanner
import com.scookie.brainscanner.device.DeviceState
import com.scookie.brainscanner.device.bitalino.BitConstants
import com.scookie.brainscanner.device.bitalino.connect.BitBTHCommunication
import com.scookie.brainscanner.device.bitalino.connect.BitDeviceScanner
import com.scookie.brainscanner.device.broadcasts.DeviceStateBroadcastReceiver
import com.scookie.brainscanner.features.devicesetup.domain.BitBluetoothDevice
import com.scookie.brainscanner.features.devicesetup.domain.fetchByAddress
import com.scookie.brainscanner.features.devicesetup.presentation.events.DeviceListingEvent
import com.scookie.brainscanner.features.devicesetup.presentation.events.DeviceListingEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeviceListingViewModel @Inject constructor(application: Application): AndroidViewModel(application), LifecycleEventObserver {

    private val context: Context get() = getApplication<Application>().applicationContext

    val scanner: DeviceScanner = BitDeviceScanner(context) {

    }

    private lateinit var bitCommunication: BitBTHCommunication

    private var selectedDeviceAddress: String = ""

    var state by mutableStateOf(DeviceListingState())

    fun onEvent(event: DeviceListingEvent) {

        when (event) {

            is StartListening    -> startListening()
            is StopListening     -> stopListening()
            is OnStartingSession -> storeDeviceConnection()
            is DeviceSelected    -> connectToDevice(event.device)

        }

    }

    private fun startListening() {
        state = state.copy(isScanning = true)

        val deviceList = scanner.fetchPairedDevices().map { BitBluetoothDevice(it, DeviceState.NO_CONNECTION) }
        updateDeviceList(deviceList)

        val stateBroadcastReceiver = DeviceStateBroadcastReceiver { deviceId, state ->
            onDeviceStateChanged(deviceId, state)
        }
        context.registerReceiver(stateBroadcastReceiver, IntentFilter(BitConstants.ACTION_STATE_CHANGED))

        scanner.startScanning()
    }

    private fun stopListening() {
        state = state.copy(isScanning = false)
        scanner.stopScanning()
    }

    private fun storeDeviceConnection() {
        DeviceHolder.saveConnection(bitCommunication)
    }

    private fun connectToDevice(device: BluetoothDevice) {

        val address = device.address
        selectedDeviceAddress = address

        bitCommunication = BitBTHCommunication(context)
        bitCommunication.connect(device.address)
    }

    private fun onDeviceStateChanged(address: String, deviceState: DeviceState) {

        if (selectedDeviceAddress == address && deviceState == DeviceState.CONNECTED) {
            Handler(Looper.getMainLooper()).postDelayed(1000) {
                if (bitCommunication.currentState != DeviceState.ACQUISITION_OK) {
                    startAcquisition()
                }
            }
        }

        val updatedList = state.devicesPaired.apply { fetchByAddress(address)?.state = deviceState }.toList()
        updateDeviceList(updatedList)
    }

    private fun startAcquisition() {
        bitCommunication.start(intArrayOf(0, 1, 2, 3, 4, 5), 100)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_DESTROY -> {
                scanner.dispose()
                bitCommunication.stop()
                bitCommunication.disconnect()
            }
        }
    }

    private fun updateDeviceList(newList: List<BitBluetoothDevice>) {
        state = state.copy(devicesPaired = emptyList())
        state = state.copy(devicesPaired = newList)
    }

}