package com.scookie.brainscanner.physiology.models

data class EEGData(
    var rightEEGWaves: FFTWaves = FFTWaves(),
    var leftEEGWaves: FFTWaves = FFTWaves(),
)
