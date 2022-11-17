package com.scookie.brainscanner.features.devicesetup.presentation.models

import androidx.annotation.DrawableRes


data class BottomMenuContent(
    val title: String,
    @DrawableRes val iconId: Int,
    val onSelect: () -> (Unit) = { }
)

