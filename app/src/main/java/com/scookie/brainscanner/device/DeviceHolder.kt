package com.scookie.brainscanner.device

import com.scookie.brainscanner.device.bitalino.connect.BitBTHCommunication

object DeviceHolder {

    private var communicationHolder: BitBTHCommunication? = null

    fun saveConnection(communication: BitBTHCommunication) {
        communicationHolder = communication
    }

    fun consume(): BitBTHCommunication = communicationHolder!!

}