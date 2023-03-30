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
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.model.KalendarEvent
import com.himanshoe.kalendar.model.KalendarType
import dev.alejo.world_holidays.ui.theme.Medium
import kotlinx.datetime.LocalDate

@Composable
fun HolidaysCalendar() {
    val events = listOf(
        KalendarEvent(
            date = LocalDate(2023, 3, 30),
            eventName = "New year's day",
            eventDescription = "This is the first day of the year"
        )
    )
    Card(shape = RoundedCornerShape(Medium)) {
        Kalendar(
            modifier = Modifier.border(border = BorderStroke(0.dp, Color.White), shape = RoundedCornerShape(Medium)),
            kalendarType = KalendarType.Firey,
            kalendarEvents = events,
            onCurrentDayClick = { day, _ ->
                Log.e("Day->", day.localDate.dayOfMonth.toString())
            })
    }
}