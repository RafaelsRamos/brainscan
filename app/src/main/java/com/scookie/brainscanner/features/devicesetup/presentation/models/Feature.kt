package com.scookie.brainscanner.features.devicesetup.presentation.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.scookie.brainscanner.common.models.FeatureShades

data class Feature(
    val title: String,
    @DrawableRes val iconId: Int,
    val featureShades: FeatureShades,
)