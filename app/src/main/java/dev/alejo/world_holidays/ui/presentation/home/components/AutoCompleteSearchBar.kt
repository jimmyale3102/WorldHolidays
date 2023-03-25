package dev.alejo.world_holidays.ui.presentation.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import dev.alejo.world_holidays.ui.theme.*

@Composable
fun AutoCompleteSearchBar(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onItemSelected: () -> Unit,
    dropDownExpanded: Boolean,
    list: List<String>,
    placeholder: String = ""
) {
    val trailingIcon = @Composable {
        IconButton(
            onClick = { },
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
    Box(
        modifier
            .background(Color.Transparent),
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) onDismissRequest()
                }
                .background(Color.Transparent),
            value = text,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder.uppercase(),
                    color = Color.White,
                    fontSize = 14.sp
                )
            },
            shape = RoundedCornerShape(Medium),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = LightWhite,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            trailingIcon = trailingIcon
        )
        DropdownMenu(
            expanded = dropDownExpanded,
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = onDismissRequest
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(item)
                        onItemSelected()
                    }
                ) {
                    Text(text = item)
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, backgroundColor = 0xFF335982)
@Composable
fun Pre() {
    val dropDownOptions = mutableStateOf(listOf<String>())
    val text by mutableStateOf("")
    val dropDownExpanded = mutableStateOf(false)

    AutoCompleteSearchBar(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        onValueChange = { },
        onDismissRequest = { },
        onItemSelected = { },
        dropDownExpanded = dropDownExpanded.value,
        list = dropDownOptions.value,
        placeholder = "COLOMBIA"
    )
}
