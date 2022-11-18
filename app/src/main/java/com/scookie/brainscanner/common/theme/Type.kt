package com.scookie.brainscanner.common.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    body1 = TextStyle(
        color = DefaultTextColor,
        fontFamily = Fonts.family,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    h1 = TextStyle(
        color = DefaultTextColor,
        fontFamily = Fonts.family,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    h2 = TextStyle(
        color = DefaultTextColor,
        fontFamily = Fonts.family,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
)