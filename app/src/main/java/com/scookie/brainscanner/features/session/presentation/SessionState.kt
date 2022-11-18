package com.scookie.brainscanner.features.session.presentation

data class SessionState(
    val eegData: List<Int> = listOf()
)