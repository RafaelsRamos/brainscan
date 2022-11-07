package com.scookie.brainscanner.device.bitalino.state

import com.scookie.brainscanner.device.bitalino.BitErrorTypes
import com.scookie.brainscanner.device.bitalino.BitException
import com.scookie.brainscanner.device.bitalino.frame.BITalinoCRC
import java.io.IOException

object BitStateDecoder {

    @Throws(IOException::class, BitException::class)
    @JvmStatic
    fun decode(id: String, buffer: ByteArray, isStateCorrect: Boolean): BitState {

        var identifier = id
        val totalBytes: Int = if (isStateCorrect) 17 else 16
        val offset = if (isStateCorrect) 0 else -1

        return try {

            val state: BitState
            val j = totalBytes - 1

            //get frame CRC
            val byteCRC = (buffer[j].toInt() and 0xFF and 0x0F).toByte()

            //CRC4 is for the all packet, from the sequence_number until the last byte of byte_n

            //test if the received CRC is equal to the one calculated
            if (java.lang.Byte.compare(byteCRC, BITalinoCRC.getCRC4(buffer)) == 0) {
                state = BitState(identifier.also { identifier = it })

                state.setDigital(0, buffer[j].toInt() shr 7 and 0x01)
                state.setDigital(1, buffer[j].toInt() shr 6 and 0x01)
                state.setDigital(2, buffer[j].toInt() shr 5 and 0x01)
                state.setDigital(3, buffer[j].toInt() shr 4 and 0x01)
                state.fill(buffer, j, offset)

                if (isStateCorrect) {
                    state.fill(buffer, j, 0)
                    state.analogOutput = buffer[j - 1].toInt() and 0xFF
                    state.batThreshold = buffer[j - 2].toInt() and 0xFF
                    state.battery = buffer[j - 3].toInt() and 0xFF shl 8 or (buffer[j - 4].toInt() and 0xFF)
                } else {
                    state.fill(buffer, j, -1)
                    state.batThreshold = buffer[j - 1].toInt() and 0xFF
                    state.battery = buffer[j - 2].toInt() and 0xFF shl 8 or (buffer[j - 3].toInt() and 0xFF)
                }
            } else {
                state = BitState(identifier)
            }
            state
        } catch (e: Exception) {
            throw BitException(BitErrorTypes.DECODE_INVALID_DATA)
        }
    }

    private fun BitState.fill(buffer: ByteArray, j: Int, offset: Int) {
        setDigital(0, buffer[j].toInt() shr 7 and 0x01)
        setDigital(1, buffer[j].toInt() shr 6 and 0x01)
        setDigital(2, buffer[j].toInt() shr 5 and 0x01)
        setDigital(3, buffer[j].toInt() shr 4 and 0x01)
        setAnalog(0, buffer[j - 5 + offset].toInt() and 0xFF shl 8 or (buffer[j - 6 + offset].toInt() and 0xFF))
        setAnalog(1, buffer[j - 7 + offset].toInt() and 0xFF shl 8 or (buffer[j - 8 + offset].toInt() and 0xFF))
        setAnalog(2, buffer[j - 9 + offset].toInt() and 0xFF shl 8 or (buffer[j - 10 + offset].toInt() and 0xFF))
        setAnalog(3, buffer[j - 11 + offset].toInt() and 0xFF shl 8 or (buffer[j - 12 + offset].toInt() and 0xFF))
        setAnalog(4, buffer[j - 13 + offset].toInt() and 0xFF shl 8 or (buffer[j - 14 + offset].toInt() and 0xFF))
        setAnalog(5, buffer[j - 15 + offset].toInt() and 0xFF shl 8 or (buffer[j - 16 + offset].toInt() and 0xFF))
    }
}