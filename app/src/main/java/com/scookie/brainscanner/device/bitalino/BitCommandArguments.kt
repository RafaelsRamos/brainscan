package com.scookie.brainscanner.device.bitalino

data class BitCommandArguments(

    var isBitalino2: Boolean = false,
    var isBLE: Boolean = false,
    var analogChannels: IntArray = intArrayOf(),
    var sampleRate: Int = 0,
    var digitalChannels: IntArray = intArrayOf(),
    var batteryThreshold: Int = 0,
    var pwmOutput: Int = 0,

)