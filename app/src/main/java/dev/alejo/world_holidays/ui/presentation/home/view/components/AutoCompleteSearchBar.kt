package dev.alejo.world_holidays.ui.presentation.home.view.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import dev.alejo.world_holidays.R
import dev.alejo.world_holidays.core.Constants
import dev.alejo.world_holidays.data.model.Country
import dev.alejo.world_holidays.ui.composables.HorizontalSpacer
import dev.alejo.world_holidays.ui.theme.*

@Composable
fun AutoCompleteSearchBar(
    modifier: Modifier = Modifier,
    value: Country,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onItemSelected: () -> Unit,
    dropDownExpanded: Boolean,
    list: List<Country>
) {
    val trailingIcon = @Composable {
        IconButton(
            onClick = { onItemSelected() },
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = stringResource(id = R.string.country_search_content_desc),
                tint = Color.White,
                modifier = Modifier.size(32.dp)
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
            value = value.name,
            leadingIcon = {
                if (value.countryCode.isEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.flag_circle),
                        tint = BlueDark,
                        contentDescription = stringResource(id = R.string.country_flag_content_desc),
                        modifier = Modifier.size(32.dp)
                    )
                } else {
                    CountryImage(countryCode = value.countryCode)
                }
            },
            onValueChange = onValueChange,
            label = {
                Text(
                    text = stringResource(id = R.string.country_search_placeholder),
                    color = BlueDark,
                    fontWeight = FontWeight.Bold
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
            keyboardActions = KeyboardActions(onSearch = { onItemSelected() }),
            trailingIcon = trailingIcon
        )
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = dropDownExpanded,
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = onDismissRequest
        ) {
            list.forEach { countryItem ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(countryItem.name.uppercase())
                        onItemSelected()
                    }
                ) {
                    CountryItem(countryItem)
                }
            }
        }
    }
}

@Composable
private fun CountryItem(countryItem: Country) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        CountryImage(countryItem.countryCode)
        HorizontalSpacer(space = Medium)
        Text(text = countryItem.name)
    }
}

@Composable
private fun CountryImage(countryCode: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(Constants.COUNTRY_FLAG_IMG_URL.format(countryCode.lowercase()))
            .decoderFactory(SvgDecoder.Factory())
            .crossfade(true)
            .placeholder(drawableResId = R.drawable.flag_circle)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, backgroundColor = 0xFF335982, showSystemUi = true)
@Composable
fun Pre() {
    val dropDownOptions = mutableStateOf(listOf<Country>(Country(name = "USA", countryCode = "us")))
    val text by mutableStateOf(Country(name = "Colombia", countryCode = "CO"))
    val dropDownExpanded = mutableStateOf(true)

    AutoCompleteSearchBar(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { },
        onDismissRequest = { },
        onItemSelected = { },
        dropDownExpanded = dropDownExpanded.value,
        list = dropDownOptions.value
    )
}
