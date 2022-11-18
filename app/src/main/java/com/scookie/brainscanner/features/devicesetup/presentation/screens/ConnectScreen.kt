package com.scookie.brainscanner.features.devicesetup.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.scookie.brainscanner.R
import com.scookie.brainscanner.common.theme.DividerColor
import com.scookie.brainscanner.common.theme.MainButtonsColor
import com.scookie.brainscanner.device.DeviceState
import com.scookie.brainscanner.features.common.presentation.LottieByUrl
import com.scookie.brainscanner.features.destinations.SessionScreenDestination
import com.scookie.brainscanner.features.devicesetup.domain.BitBluetoothDevice
import com.scookie.brainscanner.features.devicesetup.presentation.DeviceListingViewModel
import com.scookie.brainscanner.features.devicesetup.presentation.events.DeviceListingEvent

private const val BLUETOOTH_LOTTIE_URL = "https://assets7.lottiefiles.com/packages/lf20_X6tZkH.json"

@Composable
fun ConnectTab(
    navigator: DestinationsNavigator,
    viewModel: DeviceListingViewModel,
    modifier: Modifier = Modifier
) {

    val isScanning = viewModel.state.isScanning

    val deviceAcquiring = viewModel.state.devicesPaired.firstOrNull { device -> device.state == DeviceState.ACQUISITION_OK }

    if (deviceAcquiring != null) {
        viewModel.onEvent(DeviceListingEvent.OnStartingSession)
        navigator.navigate(SessionScreenDestination(deviceAddress = deviceAcquiring.device.address))
    }

    if (isScanning) {
        BluetoothLottie()
    }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {

        SearchButton(isScanning) {
            if (!isScanning) {
                viewModel.onEvent(DeviceListingEvent.StartListening)
            } else {
                viewModel.onEvent(DeviceListingEvent.StopListening)
            }
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            InstructionsSection()

            Spacer(modifier = Modifier.height(10.dp))
            Divider(thickness = 1.dp, color = DividerColor)
            Spacer(modifier = Modifier.height(10.dp))

            DeviceListing(viewModel)

        }
    }
}

@Composable
private fun BluetoothLottie() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.2F)
    ) {
        LottieByUrl(
            url = BLUETOOTH_LOTTIE_URL,
            modifier = Modifier
                .align(Alignment.Center)
                .size(200.dp)
                .offset(y = (-50).dp),
        )
    }
}

@Composable
private fun InstructionsSection() {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = "Ready for you session?",
            style = MaterialTheme.typography.h2
        )
        Text(
            text = "Find and connect to your device.",
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun DeviceListing(viewModel: DeviceListingViewModel) {

    val state = viewModel.state
    val devices = state.devicesPaired

    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        items(state.devicesPaired.size) { index -> DeviceItem(viewModel, devices[index]) }

    }


}

@Composable
private fun DeviceItem(viewModel: DeviceListingViewModel, bitDevice: BitBluetoothDevice) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { viewModel.onEvent(DeviceListingEvent.DeviceSelected(bitDevice.device)) }
    ) {
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(bitDevice.device.name)
            }
            append(" (${bitDevice.device.address})")
        }

        Text(text = annotatedString)

        Text(text = bitDevice.state.description)
    }
}

@Composable
private fun BoxScope.SearchButton(isScanning: Boolean, onClick: () -> Unit) {
    val iconRes = if (isScanning) R.drawable.ic_stop else R.drawable.ic_play
    Box(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth(0.95F)
            .offset(y = (-10).dp)
            .height(65.dp)
            .clip(RoundedCornerShape(10))
            .background(color = MainButtonsColor)
            .clickable { onClick.invoke() }
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = "Search",
            tint = Color.White,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.Center)
        )
    }
}