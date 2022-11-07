package com.scookie.brainscanner.device.bitalino.connect

import android.content.Context
import com.scookie.brainscanner.device.bitalino.BitErrorTypes
import com.scookie.brainscanner.device.bitalino.BitException
import java.util.*
import kotlin.math.ceil

abstract class BitCommunication(protected val activityContext: Context) {

    @JvmField
    protected var totalBytes = 0

    @JvmField
    protected var analogChannels = intArrayOf()

    /**
     * Initialize bit communication
     */
    abstract fun init()

    /**
     * Close the receivers
     */
    abstract fun closeReceivers()

    /**
     * Starts the acquisition mode of the device. An exception is thrown if the device is already acquiring.
     * The sampleRate must be 1Hz, 10Hz, 100Hz or 1000Hz.
     * On acquisition mode, the frames sent by the bluetooth device are received by the phone and then sent to the local broadcast receiver in the BLE case, or to the OnBITalinoDataAvailable callback in the BTH case, using the BITalinoFrame object.
     * @param analogChannels an array with the active analog channels
     * @param sampleRate the sampling frequency value
     * @return true if the command is sent successfully to the BITalino device, false otherwise
     * @throws BitException
     */
    @Throws(BitException::class)
    abstract fun start(analogChannels: IntArray?, sampleRate: Int): Boolean

    /**
     *
     * @param sampleRate
     * @return
     * @throws BitException
     */
    @Throws(BitException::class)
    protected abstract fun setFreq(sampleRate: Int): Boolean

    /**
     * Stops the acquisition mode in the device. An exception is throw if the acquisition mode is not active
     * @return true if the command is sent successfully, false otherwise
     * @throws BitException
     */
    @Throws(BitException::class)
    abstract fun stop(): Boolean

    /**
     * Tries tp cpmmect tp tje device with the given MAC address
     * @param address Media Access Control (MAC) address, the unique identifier of the device
     * @return true if the connection to the device is successful, false otherwise
     * @throws BitException
     */
    @Throws(BitException::class)
    abstract fun connect(address: String?): Boolean

    /**
     * Disconnects the device and closes the connection channel created.
     * @throws BitException
     */
    @Throws(BitException::class)
    abstract fun disconnect()

    /**
     * Get the device's firmware version
     * @return true if the command is sent successfully, false otherwise
     * @throws BitException
     */
    @get:Throws(BitException::class)
    abstract val version: Boolean

    /**
     * Sets a new battery threshold for the low-battery LED
     * @param value the new battery threshold value
     * @return true if the command is sent successfully, false otherwise
     * @throws BitException
     */
    @Throws(BitException::class)
    abstract fun battery(value: Int): Boolean

    /**
     * Assigns the digital output states
     * @param digitalChannels an array with the digital channels to enable set as 1, and digital channels to disable set as 0
     * @return true of the command is sent successfully, false otherwise
     * @throws BitException
     */
    @Throws(BitException::class)
    abstract fun trigger(digitalChannels: IntArray?): Boolean

    /**
     * Asks fot the device's current state [BITalino 2 only]
     * @return true of the command is sent successfully, false otherwise
     * @throws BitException
     */
    @Throws(BitException::class)
    abstract fun state(): Boolean

    /**
     * Assigns the analog (PWM) output value [BITalino 2 only]
     * @param pwmOutput analog output [0,255]
     * @return true of the command is sent successfully, false otherwise
     * @throws BitException
     */
    @Throws(BitException::class)
    abstract fun pwm(pwmOutput: Int): Boolean
}