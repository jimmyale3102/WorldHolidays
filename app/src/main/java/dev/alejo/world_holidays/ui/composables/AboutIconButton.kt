package dev.alejo.world_holidays.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.alejo.world_holidays.R

@Composable
fun AboutIconButton(onAboutClick: () -> Unit) {
    Box(
        Modifier.fillMaxWidth()
    ) {
        IconButton(
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                .then(Modifier.size(24.dp)),
            onClick = { onAboutClick() },
        ) {
            Icon(
                Icons.Default.Info,
                contentDescription = stringResource(id = R.string.about_app_content_desc),
                tint = Color.White
            )
        }
    }
}
