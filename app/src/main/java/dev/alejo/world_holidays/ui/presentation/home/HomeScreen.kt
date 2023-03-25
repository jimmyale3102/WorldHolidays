package dev.alejo.world_holidays.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.alejo.world_holidays.ui.*
import dev.alejo.world_holidays.ui.presentation.home.components.TextFieldWithDropdown
import dev.alejo.world_holidays.ui.theme.BlueLight
import dev.alejo.world_holidays.ui.theme.Medium
import dev.alejo.world_holidays.ui.theme.Small

@Composable
fun HomeScreen(navController: NavHostController) {
    Box(Modifier.fillMaxSize()) {
        HomeBackground()

        Column(
            Modifier
                .fillMaxWidth()
                .padding(Medium)
        ) {
            IconButton(
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .then(Modifier.size(24.dp)),
                onClick = {},
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = "",
                    tint = Color.White
                )
            }
            Spacer(Modifier.height(Medium))
            TextFieldWithDropdown(
                modifier = Modifier.padding(horizontal = Small),
                value = textFieldValue.value,
                setValue = ::onValueChanged,
                onDismissRequest = ::onDropdownDismissRequest,
                dropDownExpanded = dropDownExpanded.value,
                list = dropDownOptions.value,
                label = "Label"
            )
        }

        Column(
            Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "New Year's Day",
                color = Color.White,
                fontSize = 34.sp
            )
            Text(
                text = "New Year's Day is the first day of the year",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun HomeBackground() {
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
                .data("https://github.com/jimmyale3102/World-Holidays-Assets/blob/master/7.jpg?raw=true")
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



val all = listOf("aaa", "baa", "aab", "abb", "bab", "bbbbb")

val dropDownOptions = mutableStateOf(listOf<String>())
val textFieldValue = mutableStateOf(TextFieldValue())
val dropDownExpanded = mutableStateOf(false)

fun onDropdownDismissRequest() {
    dropDownExpanded.value = false
}

fun onValueChanged(value: TextFieldValue) {
    dropDownExpanded.value = true
    textFieldValue.value = value
    dropDownOptions.value = all.filter { it.startsWith(value.text) && it != value.text }.take(3)
}