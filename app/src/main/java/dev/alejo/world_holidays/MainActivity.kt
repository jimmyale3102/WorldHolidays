package dev.alejo.world_holidays

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import dev.alejo.world_holidays.ui.presentation.home.HomeViewModel
import dev.alejo.world_holidays.ui.presentation.home.components.TextFieldWithDropdown
import dev.alejo.world_holidays.ui.theme.WorldHolidaysTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val all = listOf("aaa", "baa", "aab", "abb", "bab")

    val dropDownOptions = mutableStateOf(listOf<String>())
    val textFieldValue = mutableStateOf(TextFieldValue())
    val dropDownExpanded = mutableStateOf(false)

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorldHolidaysTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: HomeViewModel by viewModels()
                    viewModel.onCreate(this)
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ){
                        Greeting("Android")

                        TextFieldWithDropdown(
                            modifier = Modifier.fillMaxWidth(),
                            value = textFieldValue.value,
                            setValue = ::onValueChanged,
                            onDismissRequest = ::onDropdownDismissRequest,
                            dropDownExpanded = dropDownExpanded.value,
                            list = dropDownOptions.value,
                            label = "Label"
                        )
                    }
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
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorldHolidaysTheme {
        Greeting("Android")
    }
}