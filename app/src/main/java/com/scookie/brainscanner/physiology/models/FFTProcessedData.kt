package com.scookie.brainscanner.physiology.models

data class FFTProcessedData(
    var ppgData: PPGData = PPGData(),
    var eegData: EEGData = EEGData(),
)