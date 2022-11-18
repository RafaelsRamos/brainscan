package com.scookie.brainscanner.features.session.presentation.events

sealed class SessionEvents {

    data class ObserveDevice(val address: String): SessionEvents()

}