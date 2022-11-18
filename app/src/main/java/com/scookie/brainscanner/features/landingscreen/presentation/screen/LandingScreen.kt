@file:OptIn(ExperimentalFoundationApi::class)

package com.scookie.brainscanner.features.landingscreen.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.scookie.brainscanner.common.theme.*
import com.scookie.brainscanner.features.common.presentation.LottieByUrl
import com.scookie.brainscanner.features.destinations.DeviceListingScreenDestination

private const val BRAIN_LOTTIE_URL = "https://assets4.lottiefiles.com/packages/lf20_33asonmr.json"

@Destination(start = true)
@Composable
fun LandingScreen(
    navigator: DestinationsNavigator
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(AppBackground)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 80.dp),
            text = "Brain App",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 45.sp,
            color = Color.Black,
            fontFamily = Fonts.titleFamily,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.50F),
            contentAlignment = Alignment.Center,
        ) {
            LottieByUrl(BRAIN_LOTTIE_URL)
        }

        Spacer(modifier = Modifier.height(20.dp))

        StartButton("Dive in") {
            navigator.navigate(DeviceListingScreenDestination)
        }

    }

}

@Composable
private fun StartButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.85F)
            .fillMaxHeight(0.185F)
            .padding(bottom = 5.dp)
            .clip(RoundedCornerShape(200.dp))
            .background(color = MainButtonsColor)
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxHeight(),
            text = text,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MainButtonsTextColor,
            fontFamily = Fonts.family,
            fontSize = 25.sp
        )
    }
}