package com.scookie.brainscanner.device.bitalino.frame

import com.scookie.brainscanner.device.bitalino.BitErrorTypes
import com.scookie.brainscanner.device.bitalino.BitException
import java.io.IOException

object BitFrameDecoder {

    @Throws(IOException::class, BitException::class)
    @JvmStatic
    fun decode(identifier: String?, buffer: ByteArray, analogChannels: IntArray, totalBytes: Int): BitFrame {
        return try {

            // Get frame CRC
            val byteCRC = (buffer[totalBytes - 1].toInt() and 0xFF and 0x0F).toByte()

            // Test if the received CRC is equal to the one calculated

            if (byteCRC.compareTo(BITalinoCRC.getCRC4(buffer)) == 0) {
                BitFrame(
                    identifier = identifier,
                    sequence = buffer[totalBytes - 1].toInt() and 0xF0 shr 4 and 0xf,
                    buffer = buffer,
                    totalBytes = totalBytes
                )
            } else {
                BitFrame(identifier, sequence = -1)
            }
        } catch (e: Exception) {
            throw BitException(BitErrorTypes.DECODE_INVALID_DATA)
        }
    }
}