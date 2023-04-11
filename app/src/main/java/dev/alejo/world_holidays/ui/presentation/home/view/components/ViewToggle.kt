package dev.alejo.world_holidays.ui.presentation.home.view.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alejo.world_holidays.R
import dev.alejo.world_holidays.ui.theme.BlueLight
import dev.alejo.world_holidays.ui.theme.Medium
import dev.alejo.world_holidays.ui.theme.WhiteCake

/**
 * This component shows the only 2 possible options to display the holidays
 * @param modifier The modifier to be applied to the ViewToggle.
 * @param isListViewSelected Contains the boolean value if the list view is selected.
 * @param onViewSelected Lambda expression to send a boolean value if the list view is selected.
 */

@Composable
fun ViewToggle(
    modifier: Modifier = Modifier,
    isListViewSelected: Boolean,
    onViewSelected: (Boolean) -> Unit
) {

    Row(modifier = modifier) {
        Button(
            modifier = Modifier.width(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isListViewSelected)
                    IconSelectedStyle.background
                else
                    IconUnselectedStyle.background
            ),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(
                topStart = Medium,
                bottomStart = Medium,
                topEnd = 0.dp,
                bottomEnd = 0.dp
            ),
            onClick = { onViewSelected(true) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.list_ic),
                tint = if (isListViewSelected) IconSelectedStyle.tint else IconUnselectedStyle.tint,
                contentDescription = stringResource(
                    id = R.string.list_view_icon_content_desc
                )
            )
        }

        Button(
            modifier = Modifier.width(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isListViewSelected)
                    IconUnselectedStyle.background
                else
                    IconSelectedStyle.background
            ),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = Medium,
                bottomEnd = Medium
            ),
            onClick = { onViewSelected(false) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.calendar_ic),
                tint = if (isListViewSelected) IconUnselectedStyle.tint else IconSelectedStyle.tint,
                contentDescription = stringResource(
                    id = R.string.calendar_view_icon_content_desc
                )
            )
        }
    }
}

private object IconSelectedStyle {
    val tint = WhiteCake
    val background = BlueLight
}

private object IconUnselectedStyle {
    val tint = BlueLight
    val background = WhiteCake
}

@Preview(showBackground = true)
@Composable
fun ViewTogglePreview() {
    ViewToggle(isListViewSelected = true) { }
}