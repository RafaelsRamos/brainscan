package com.scookie.brainscanner.features.devicesetup.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.scookie.brainscanner.common.theme.TextWhite
import com.scookie.brainscanner.features.devicesetup.presentation.DeviceListingViewModel
import com.scookie.brainscanner.features.devicesetup.presentation.events.DeviceListingEvent
import timber.log.Timber

@Destination(start = true)
@Composable
fun DeviceListingScreen(
    navigator: DestinationsNavigator,
    viewModel: DeviceListingViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        ButtonsSection(viewModel)

        DeviceListing(viewModel)

    }

}

@Composable
fun ButtonsSection(viewModel: DeviceListingViewModel) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        SimpleButton("Search devices") {
            viewModel.onEvent(DeviceListingEvent.StartListening)
            Timber.tag("Test").v("Searching devices - $viewModel")
        }

        SimpleButton("Stop search") {
            viewModel.onEvent(DeviceListingEvent.StopListening)
            Timber.tag("Test").v("Stop search for devices")
        }

        SimpleButton("Clear all") {
            viewModel.onEvent(DeviceListingEvent.ClearAll)
            Timber.tag("Test").v("Clear all devices")
        }

    }

}

@Composable
fun DeviceListing(viewModel: DeviceListingViewModel) {

    val state = viewModel.state
    val devices = state.devicesPaired

    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        items(state.devicesPaired.size) { index ->

            Column(modifier = Modifier.fillMaxWidth()) {
                val bitDevice = devices[index]
                val annotatedString = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(bitDevice.device.name)
                    }
                    append(" (${bitDevice.device.address})")
                }

                Text(
                    modifier = Modifier.clickable { viewModel.onEvent(DeviceListingEvent.DeviceSelected(bitDevice.device)) },
                    text = annotatedString
                )

                Text(
                    text = bitDevice.state.description
                )
            }

        }

    }


}

@Composable
private fun SimpleButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(40.dp)
            .background(
                color = Color.Blue,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = TextWhite
        )
    }
}