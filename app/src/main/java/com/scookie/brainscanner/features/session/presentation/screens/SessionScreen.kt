package com.scookie.brainscanner.features.session.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.scookie.brainscanner.features.session.presentation.SessionViewModel
import com.scookie.brainscanner.features.session.presentation.events.SessionEvents

@Destination
@Composable
fun SessionScreen(
    navigator: DestinationsNavigator,
    deviceAddress: String,
    viewModel: SessionViewModel = hiltViewModel()
) {

    viewModel.onEvent(SessionEvents.ObserveDevice(deviceAddress))

}