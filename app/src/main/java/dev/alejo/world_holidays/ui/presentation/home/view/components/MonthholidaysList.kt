package dev.alejo.world_holidays.ui.presentation.home.view.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.alejo.world_holidays.R
import dev.alejo.world_holidays.data.model.HolidayModel
import dev.alejo.world_holidays.ui.theme.Medium
import dev.alejo.world_holidays.ui.theme.Yellow

@Composable
fun MonthHolidaysList(
    @StringRes listTitle: Int = R.string.all_holidays,
    holidaysList: List<HolidayModel>
) {
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(Medium)) {
        Text(
            text = stringResource(id = listTitle),
            fontSize = 30.sp,
            color = Yellow,
            fontWeight = FontWeight.Bold
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(Medium)) {
            items(holidaysList) { holiday ->
                HolidayItem(holidayItem = holiday)
            }
        }
    }
}