package com.scookie.brainscanner.features.devicesetup.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scookie.brainscanner.R
import com.scookie.brainscanner.common.theme.*
import com.scookie.brainscanner.features.devicesetup.presentation.models.Feature

@Composable
fun HomeTab() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GreetingSection("Rafa")

        Spacer(modifier = Modifier.height(4.dp))

        FeatureListing()

    }
}

@Composable
fun GreetingSection(
    name: String = "Philipp"
) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = "Good morning, $name",
            style = MaterialTheme.typography.h2
        )
        Text(
            text = "Welcome back!",
            style = MaterialTheme.typography.body1
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FeatureListing() {
    FeatureSection(
        features = listOf(
            Feature(
                title = "Sleep meditation",
                iconId = R.drawable.ic_headphone,
                featureShades = FeatureTones.blueViolet
            ),
            Feature(
                title = "Tips for sleeping",
                iconId = R.drawable.ic_videocam,
                featureShades = FeatureTones.lightGreen
            ),
            Feature(
                title = "Night island",
                iconId = R.drawable.ic_headphone,
                featureShades = FeatureTones.orangeYellow
            ),
            Feature(
                title = "Calming sounds",
                iconId = R.drawable.ic_headphone,
                featureShades = FeatureTones.beige
            ),
            Feature(
                title = "Relaxing experience",
                iconId = R.drawable.ic_videocam,
                featureShades = FeatureTones.teal
            ),
            Feature(
                title = "Mindfulness",
                iconId = R.drawable.ic_headphone,
                featureShades = FeatureTones.orangeYellow
            ),
        )
    )
}