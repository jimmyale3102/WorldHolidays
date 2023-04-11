package dev.alejo.world_holidays.ui.presentation.home.view.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.alejo.world_holidays.core.kalendar.Kalendar
import dev.alejo.world_holidays.core.kalendar.model.KalendarEvent
import dev.alejo.world_holidays.core.kalendar.model.KalendarType
import dev.alejo.world_holidays.core.uitls.DateUtils
import dev.alejo.world_holidays.data.model.HolidayModel
import dev.alejo.world_holidays.ui.theme.Medium
import kotlinx.datetime.*

@Composable
fun HolidaysCalendar(holidaysByMonth: List<HolidayModel>, onPageChanged: (Int, Month) -> Unit) {
    val events = holidaysByMonth.map { holidayItem ->
        KalendarEvent(
            date = DateUtils.getLocalDateFromString(holidayItem.date), eventName = holidayItem.name
        )
    }
    Card(shape = RoundedCornerShape(Medium)) {
        Kalendar(
            modifier = Modifier.border(
                border = BorderStroke(0.dp, Color.White),
                shape = RoundedCornerShape(Medium)
            ),
            kalendarType = KalendarType.Firey,
            kalendarEvents = events,
            onCurrentDayClick = { day, _ ->
                Log.e("Day->", day.localDate.dayOfMonth.toString())
            },
            onPreviousClick = { currentYear, currentMonth ->
                Log.e(
                    "Year and Month->",
                    "$currentYear ${currentMonth.name}"
                )
                onPageChanged(currentYear, currentMonth)
            },
            onNextClick = { currentYear, currentMonth ->
                Log.e(
                    "Year and Month->",
                    "$currentYear ${currentMonth.name}"
                )
                onPageChanged(currentYear, currentMonth)
            }
        )
    }
}