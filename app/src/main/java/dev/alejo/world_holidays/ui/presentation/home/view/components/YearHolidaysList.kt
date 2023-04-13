package dev.alejo.world_holidays.ui.presentation.home.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.alejo.world_holidays.core.uitls.DateUtils
import dev.alejo.world_holidays.data.model.HolidayModel
import dev.alejo.world_holidays.ui.theme.Medium
import dev.alejo.world_holidays.ui.theme.XSmall
import dev.alejo.world_holidays.ui.theme.Yellow

@Composable
fun YearHolidaysList(holidaysList: List<HolidayModel>) {
    // Month number starts in 1 (Jan) and ends in 12 (Dec)
    var currentMonth by rememberSaveable { mutableStateOf(0) }
    var monthNumber by rememberSaveable { mutableStateOf(0) }
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Medium)
    ) {
        holidaysList.forEach { holiday ->
            monthNumber = DateUtils.getMonthNumber(holiday.date)
            if (currentMonth != monthNumber) {
                currentMonth = monthNumber
                val monthName = DateUtils.getDateFromString(holiday.date)?.let {
                    DateUtils.getMonthName(it)
                } ?: "-"
                MonthName(monthName = monthName)
            }
            HolidayItem(holidayItem = holiday)
        }
    }
}

@Composable
private fun MonthName(monthName: String) {
    Text(
        text = monthName,
        fontSize = 30.sp,
        color = Yellow,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = Medium)
    )
}