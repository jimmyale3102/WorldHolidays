package dev.alejo.world_holidays

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import dev.alejo.world_holidays.ui.presentation.home.HomeViewModel
import dev.alejo.world_holidays.ui.presentation.home.components.TextFieldWithDropdown
import dev.alejo.world_holidays.ui.theme.*
import retrofit2.http.Body

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val all = listOf("aaa", "baa", "aab", "abb", "bab")

    private val dropDownOptions = mutableStateOf(listOf<String>())
    private val textFieldValue = mutableStateOf(TextFieldValue())
    private val dropDownExpanded = mutableStateOf(false)

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorldHolidaysTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PrimaryGreen
                ) {
                    val viewModel: HomeViewModel by viewModels()
                    viewModel.onCreate(this)

                    HolidayBody()
                }
            }
        }
    }

    private fun onDropdownDismissRequest() {
        dropDownExpanded.value = false
    }

    private fun onValueChanged(value: TextFieldValue) {
        dropDownExpanded.value = true
        textFieldValue.value = value
        dropDownOptions.value = all.filter { it.startsWith(value.text) && it != value.text }.take(3)
    }

}

@Composable
fun HolidayBody() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(Medium)
    ) {
        Column(
            Modifier.fillMaxWidth()
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
                    tint = PrimaryGreen
                )
            }
            Spacer(Modifier.height(Large))
            TextFieldWithDropdown(
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WorldHolidaysTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = PrimaryGreen
        ) {

            HolidayBody()
        }
    }
}



val all = listOf("aaa", "baa", "aab", "abb", "bab")

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