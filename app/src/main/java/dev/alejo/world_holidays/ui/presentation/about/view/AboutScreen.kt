package dev.alejo.world_holidays.ui.presentation.about.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.alejo.world_holidays.R
import dev.alejo.world_holidays.core.Constants
import dev.alejo.world_holidays.ui.composables.VerticalSpacer
import dev.alejo.world_holidays.ui.theme.*

@Composable
fun AboutScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val jimmyIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(Constants.JIMMY_URL)) }
    val picturesIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PIXABAY_URL)) }

    Box(
        Modifier
            .fillMaxSize()
            .background(BlueLight)
            .padding(Medium)
    ) {
        BackIcon(navHostController)

        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Yellow,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            VerticalSpacer(space = Medium)
            Text(
                text = stringResource(id = R.string.about_app),
                color = WhiteCake,
                textAlign = TextAlign.Center
            )
            VerticalSpacer(space = Medium)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                MadeByLoveText()
                Text(
                    modifier = Modifier.clickable { context.startActivity(jimmyIntent) },
                    text = stringResource(id = R.string.Jimmy),
                    color = Yellow,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic
                )
            }
            VerticalSpacer(space = XSmall)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(id = R.string.pictures_are_from),
                    color = WhiteCake,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    modifier = Modifier.clickable { context.startActivity(picturesIntent) },
                    text = stringResource(id = R.string.pixabay),
                    color = Yellow,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic
                )
            }
        }

        Text(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = stringResource(id = R.string.app_version),
            color = WhiteCake,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic
        )
    }
}

@Composable
private fun BackIcon(navHostController: NavHostController) {
    IconButton(
        modifier = Modifier.size(24.dp),
        onClick = { navHostController.popBackStack() },
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Yellow
        )
    }
}

@Composable
private fun MadeByLoveText() {
    val iconId = "heartIcon"
    val text = buildAnnotatedString {
        append(stringResource(id = R.string.about_app_made_by))
        append(" ")
        appendInlineContent(iconId, "[icon]")
        append(" ")
        append(stringResource(id = R.string.by))
        append(" ")
    }

    val inlineContent = mapOf(
        Pair(
            iconId,
            InlineTextContent(
                Placeholder(
                    width = 12.sp,
                    height = 12.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline
                )
            ) {
                Icon(Icons.Filled.Favorite, null, tint = Yellow)
            }
        )
    )
    Text(
        text = text,
        inlineContent = inlineContent,
        color = WhiteCake,
        textAlign = TextAlign.Center,
        fontStyle = FontStyle.Italic
    )
}