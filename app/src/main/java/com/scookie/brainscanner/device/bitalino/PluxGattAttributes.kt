package com.scookie.brainscanner.device.bitalino

/**
 * This class includes the PLUX's GATT attributes
 */
object PluxGattAttributes {

    const val CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb"
    const val COMMANDS = "4051eb11-bf0a-4c74-8730-a48f4193fcea"
    const val FRAMES = "40fdba6b-672e-47c4-808a-e529adff3633"
    const val DataS = "c566488a-0882-4e1b-a6d0-0b717e652234"

    private val attributes: HashMap<String, String> = hashMapOf(
        "0000180a-0000-1000-8000-00805f9b34fb" to "Device Information Service",
        "00002a29-0000-1000-8000-00805f9b34fb" to "Manufacturer Name String",
        "00002a26-0000-1000-8000-00805f9b34fb" to "Firmware Version",
        "00001800-0000-1000-8000-00805f9b34fb" to "Name Service",
        "00002a00-0000-1000-8000-00805f9b34fb" to "Device Name",
        DataS                                  to "Data Service",
        FRAMES                                 to "Frames characteristic",
        COMMANDS                               to "Commands characteristic",
    )

    @JvmStatic
    fun lookup(uuid: String?, defaultName: String): String {
        val name = attributes[uuid]
        return name ?: defaultName
    }
}