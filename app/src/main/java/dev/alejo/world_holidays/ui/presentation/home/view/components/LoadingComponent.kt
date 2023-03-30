package dev.alejo.world_holidays.ui.presentation.home.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import dev.alejo.world_holidays.R

@Composable
fun LoadingComponent() {
    Box(modifier = Modifier.fillMaxSize()) {
        val composition1 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_circle))
        val progressAnimation = animateLottieCompositionAsState(
            composition1,
            isPlaying = true,
            iterations = LottieConstants.IterateForever,
            speed = 1f
        )
        LottieAnimation(
            modifier = Modifier.align(Alignment.Center).size(200.dp),
            composition = composition1,
            progress = { progressAnimation.progress }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoadingPreview() {
    LoadingComponent()
}