package com.scookie.brainscanner.features.devicesetup.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scookie.brainscanner.common.theme.ButtonBlue
import com.scookie.brainscanner.common.theme.TextWhite
import com.scookie.brainscanner.features.devicesetup.presentation.models.Feature
import com.scookie.brainscanner.utils.drawPathWith

@Composable
fun FeatureItem(
    feature: Feature
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(feature.featureShades.shadeOne)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        // Medium colored path
        val mediumPoint1 = Offset(0f, height * 0.3f)
        val mediumPoint2 = Offset(width * 0.1f, height * 0.35f)
        val mediumPoint3 = Offset(width * 0.4f, height * 0.05f)
        val mediumPoint4 = Offset(width * 0.75f, height * 0.7f)
        val mediumPoint5 = Offset(width * 1.4f, -height.toFloat())
        val mediumColorPoints = listOf(mediumPoint1, mediumPoint2, mediumPoint3, mediumPoint4, mediumPoint5)
        val mediumColoredPath = constraints.drawPathWith(mediumColorPoints)

        // Light colored path
        val lightPoint1 = Offset(0f, height * 0.35f)
        val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)
        val lightColorPoints = listOf(lightPoint1, lightPoint2, lightPoint3, lightPoint4, lightPoint5)
        val lightColoredPath = constraints.drawPathWith(lightColorPoints)

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawPath(
                path = mediumColoredPath,
                color = feature.featureShades.shadeTwo
            )
            drawPath(
                path = lightColoredPath,
                color = feature.featureShades.shadeThree
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = feature.title,
                style = MaterialTheme.typography.h2,
                lineHeight = 26.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Icon(
                painter = painterResource(id = feature.iconId),
                contentDescription = feature.title,
                tint = Color.White,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Text(
                text = "Start",
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        // Handle the click
                    }
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }
}