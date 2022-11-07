package com.scookie.brainscanner.device

enum class DeviceState(val id: Int, val description: String) {

    NO_CONNECTION(
        id = 0,
        description = "Not connected"
    ),
    LISTEN(
        id = 1,
        description = "Listening"
    ),
    CONNECTING(
        id = 2,
        description = "Connecting"
    ),
    CONNECTED(
        id = 3,
        description =  "Connected"
    ),
    ACQUISITION_TRYING(
        id = 4,
        description =  "Starting acquisition"
    ),
    ACQUISITION_OK(
        id = 5,
        description = "Acquiring"
    ),
    ACQUISITION_STOPPING(
        id = 6,
        description = "Stopping acquisition"
    ),
    DISCONNECTED(
        id = 7,
        description = "Disconnected"
    ),
    ENDED(
        id = 8,
        description = "Ended"
    );

    companion object {

        fun getStateById(id: Int): DeviceState = values().firstOrNull { it.id == id } ?: NO_CONNECTION

    }

}