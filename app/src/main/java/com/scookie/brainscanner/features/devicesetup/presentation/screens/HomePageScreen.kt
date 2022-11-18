package com.scookie.brainscanner.features.devicesetup.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.scookie.brainscanner.R
import com.scookie.brainscanner.common.theme.AppBackground
import com.scookie.brainscanner.common.theme.DividerColor
import com.scookie.brainscanner.features.devicesetup.presentation.DeviceListingViewModel
import com.scookie.brainscanner.features.devicesetup.presentation.models.BottomMenuContent
import com.scookie.brainscanner.features.devicesetup.presentation.models.Feature

@ExperimentalFoundationApi
@Destination
@Composable
fun DeviceListingScreen(
    navigator: DestinationsNavigator,
    viewModel: DeviceListingViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var selectedItemIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .background(AppBackground)
            .fillMaxSize()
    ) {

        when (selectedItemIndex) {
            0 -> HomeTab()
            2 -> ConnectTab(
                navigator,
                viewModel,
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9F)
            )
            else -> { }
        }


        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(thickness = 1.dp, color = DividerColor)
            BottomMenu(
                initialSelectedItemIndex = selectedItemIndex,
                modifier = Modifier.fillMaxWidth(0.95F),
                items = listOf(
                    BottomMenuContent(
                        title = "Home",
                        iconId = R.drawable.ic_home
                    ) { selectedItemIndex = 0 },
                    BottomMenuContent(
                        title = "Meditate",
                        iconId = R.drawable.ic_bubble
                    ),
                    BottomMenuContent(
                        title = "Connect",
                        iconId = R.drawable.ic_bluetooth
                    ) { selectedItemIndex = 2 },
                    BottomMenuContent(
                        title = "Stats",
                        iconId = R.drawable.ic_chart
                    ),
                    BottomMenuContent(
                        title = "Profile",
                        iconId = R.drawable.ic_profile
                    ),
                )
            )
        }

    }
}

@ExperimentalFoundationApi
@Composable
fun FeatureSection(features: List<Feature>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Features",
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(15.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(features.size) {
                FeatureItem(feature = features[it])
            }
        }
    }
}