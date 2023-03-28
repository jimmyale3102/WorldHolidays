package dev.alejo.world_holidays.ui.presentation.home.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.alejo.world_holidays.core.Constants
import dev.alejo.world_holidays.ui.theme.BlueLight

@Composable
fun HomeBackground() {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        BlueLight
                    ),
                    startY = 0F,
                    endY = 600F
                )
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.HOME_BACKGROUND_IMG_URL.format(getRandomNumber()))
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3F)
        )
    }
}

private fun getRandomNumber() = (0..10).random()