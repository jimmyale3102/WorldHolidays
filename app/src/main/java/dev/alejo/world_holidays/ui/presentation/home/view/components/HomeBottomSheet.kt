package dev.alejo.world_holidays.ui.presentation.home.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alejo.world_holidays.R
import dev.alejo.world_holidays.ui.theme.BlueDark
import dev.alejo.world_holidays.ui.theme.Medium
import dev.alejo.world_holidays.ui.theme.Small
import dev.alejo.world_holidays.ui.theme.XLarge

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeBottomSheet(bottomSheetContent: @Composable () -> Unit) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(
            topStart = XLarge,
            topEnd = XLarge
        ),
        sheetContent = {
            PeekContent()
        },
        sheetBackgroundColor = BlueDark,
        drawerElevation = Small,
        sheetPeekHeight = XLarge
    ) {
        bottomSheetContent()
    }
}

@Composable
private fun PeekContent() {
    var isListViewSelected by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Medium),
        verticalArrangement = Arrangement.spacedBy(Medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            Modifier
                .width(50.dp)
                .height(4.dp)
                .background(Color.White)
        )
        Text(
            text = stringResource(id = R.string.all_holidays),
            fontSize = 16.sp,
            color = Color.White
        )
        ViewToggle(
            modifier = Modifier.align(Alignment.End),
            isListViewSelected
        ) { listViewSelected ->
            isListViewSelected = listViewSelected
        }

        if (isListViewSelected) {
            // Show List view
        } else {
            // show Calendar view
        }

    }
}