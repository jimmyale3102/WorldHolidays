package dev.alejo.world_holidays.ui.presentation.home.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import dev.alejo.world_holidays.R
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
                    endY = 500F
                )
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.2F),
            painter = painterResource(id = PainterBackground.getRandom()),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

object PainterBackground {
    private val backgrounds = arrayOf(
        R.drawable.bg_0,
        R.drawable.bg_1,
        R.drawable.bg_2,
        R.drawable.bg_3,
        R.drawable.bg_4,
        R.drawable.bg_5,
        R.drawable.bg_6,
        R.drawable.bg_7,
        R.drawable.bg_8,
        R.drawable.bg_9,
        R.drawable.bg_10,
        R.drawable.bg_11,
        R.drawable.bg_12,
        R.drawable.bg_13,
        R.drawable.bg_14,
        R.drawable.bg_15,
        R.drawable.bg_16,
        R.drawable.bg_17,
        R.drawable.bg_18,
        R.drawable.bg_19
    )

    fun getRandom() = backgrounds.random()
}