package com.scookie.brainscanner.features.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*


@Composable
fun LottieByUrl(
    url: String,
    modifier: Modifier = Modifier,
    iterations: Int = LottieConstants.IterateForever,
    speed: Float = 1F,
    onAnimationFinished: () -> Unit = { }
) {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Url(url))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = iterations,
        speed = speed,
    )

    if (progress >= 1) {
        onAnimationFinished()
    }

}