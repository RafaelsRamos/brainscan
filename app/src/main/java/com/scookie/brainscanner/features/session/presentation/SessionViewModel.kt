package com.scookie.brainscanner.features.session.presentation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.scookie.brainscanner.device.DeviceHolder
import com.scookie.brainscanner.device.bitalino.connect.BitBTHCommunication
import com.scookie.brainscanner.device.bitalino.frame.BitFrame
import com.scookie.brainscanner.features.session.presentation.events.SessionEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(application: Application): AndroidViewModel(application), LifecycleEventObserver {

    var state by mutableStateOf(SessionState())

    private lateinit var  bitCommunication: BitBTHCommunication

    fun onEvent(event: SessionEvents) {

        when (event) {
            is SessionEvents.ObserveDevice -> {
                bitCommunication = DeviceHolder.consume()
                bitCommunication.setOnFrameAvailable { frame -> onFrameAvailable(frame) }
            }
        }
    }

    private fun onFrameAvailable(frame: BitFrame) {
        val first = frame.analogArray[0]
        val second = frame.analogArray[1]
        val third = frame.analogArray[2]
        val forth = frame.analogArray[3]
        val fifth = frame.analogArray[4]
        println("Array -> $first | $second | $third | $forth | $fifth")
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_DESTROY -> {
            }
        }
    }

}