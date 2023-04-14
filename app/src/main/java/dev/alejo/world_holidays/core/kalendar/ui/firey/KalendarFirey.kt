/*
 * Copyright 2022 Kalendar Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.alejo.world_holidays.core.kalendar.ui.firey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.alejo.world_holidays.core.kalendar.color.KalendarThemeColor
import dev.alejo.world_holidays.core.kalendar.component.day.KalendarDay
import dev.alejo.world_holidays.core.kalendar.component.day.config.KalendarDayColors
import dev.alejo.world_holidays.core.kalendar.component.header.KalendarHeader
import dev.alejo.world_holidays.core.kalendar.component.header.config.KalendarHeaderConfig
import dev.alejo.world_holidays.core.kalendar.component.text.KalendarNormalText
import dev.alejo.world_holidays.core.kalendar.component.text.config.KalendarTextColor
import dev.alejo.world_holidays.core.kalendar.component.text.config.KalendarTextConfig
import dev.alejo.world_holidays.core.kalendar.component.text.config.KalendarTextSize
import dev.alejo.world_holidays.core.kalendar.model.KalendarDay
import dev.alejo.world_holidays.core.kalendar.model.KalendarEvent
import dev.alejo.world_holidays.core.kalendar.model.toKalendarDay
import dev.alejo.world_holidays.ui.theme.BlueLight
import dev.alejo.world_holidays.ui.theme.Yellow
import kotlinx.datetime.*

val WeekDays = listOf("M", "T", "W", "T", "F", "S", "S")

@Composable
fun KalendarFirey(
    modifier: Modifier = Modifier,
    takeMeToDate: LocalDate?,
    kalendarDayColors: KalendarDayColors,
    kalendarHeaderConfig: KalendarHeaderConfig? = null,
    kalendarThemeColors: List<KalendarThemeColor>,
    kalendarEvents: List<KalendarEvent> = emptyList(),
    onCurrentDayClick: (KalendarDay, List<KalendarEvent>) -> Unit = { _, _ -> },
    onPreviousClick: (Int, Month) ->  Unit,
    onNextClick: (Int, Month) ->  Unit
) {
    val currentDay = takeMeToDate ?: Clock.System.todayIn(TimeZone.currentSystemDefault())
    val displayedMonth = remember {
        mutableStateOf(currentDay.month)
    }
    val displayedYear = remember {
        mutableStateOf(currentDay.year)
    }
    val currentMonth = displayedMonth.value
    val currentYear = displayedYear.value

    val daysInMonth = currentMonth.minLength()
    val monthValue =
        if (currentMonth.value.toString().length == 1) "0" + currentMonth.value.toString() else currentMonth.value.toString()
    val startDayOfMonth = "$currentYear-$monthValue-01".toLocalDate()
    val firstDayOfMonth = startDayOfMonth.dayOfWeek
    val selectedKalendarDate = remember { mutableStateOf(currentDay) }
    val newKalenderHeaderConfig = KalendarHeaderConfig(
        kalendarTextConfig = KalendarTextConfig(
            kalendarTextSize = KalendarTextSize.SubTitle,
            kalendarTextColor = KalendarTextColor(
                kalendarThemeColors[currentMonth.value.minus(1)].headerTextColor,
            )
        )
    )

    Column(
        modifier = modifier
            .background(
                color = kalendarThemeColors[currentMonth.value.minus(1)].backgroundColor
            )
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        KalendarHeader(
            modifier = Modifier.padding(vertical = 12.dp),
            month = displayedMonth.value,
            onPreviousClick = {
                if (displayedMonth.value.value == 1) {
                    displayedYear.value = displayedYear.value.minus(1)
                }
                displayedMonth.value = displayedMonth.value.minus(1)
                onPreviousClick(displayedYear.value, displayedMonth.value)
            },
            onNextClick = {
                if (displayedMonth.value.value == 12) {
                    displayedYear.value = displayedYear.value.plus(1)
                }
                displayedMonth.value = displayedMonth.value.plus(1)
                onNextClick(displayedYear.value, displayedMonth.value)
            },
            year = displayedYear.value,
            kalendarHeaderConfig = kalendarHeaderConfig ?: newKalenderHeaderConfig
        )
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(7),
            content = {
                items(WeekDays) {
                    KalendarNormalText(
                        text = it,
                        fontWeight = FontWeight.Normal,
                        textColor = kalendarDayColors.textColor,
                    )
                }
                items((getInitialDayOfMonth(firstDayOfMonth)..daysInMonth).toList()) {
                    if (it > 0) {
                        val day = getGeneratedDay(it, currentMonth, currentYear)
                        val isCurrentDay = day == currentDay
                        KalendarDay(
                            kalendarDay = day.toKalendarDay(),
                            modifier = Modifier,
                            kalendarEvents = kalendarEvents,
                            isCurrentDay = isCurrentDay,
                            onCurrentDayClick = { kalendarDay, events ->
                                selectedKalendarDate.value = kalendarDay.localDate
                                onCurrentDayClick(kalendarDay, events)
                            },
                            selectedKalendarDay = selectedKalendarDate.value,
                            kalendarDayColors = kalendarDayColors,
                            //dotColor = kalendarThemeColors[currentMonth.value.minus(1)].headerTextColor,
                            //dayBackgroundColor = kalendarThemeColors[currentMonth.value.minus(1)].dayBackgroundColor,
                            dotColor = Yellow,
                            dayBackgroundColor = BlueLight,
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun KalendarFirey(
    modifier: Modifier = Modifier,
    kalendarEvents: List<KalendarEvent> = emptyList(),
    onCurrentDayClick: (KalendarDay, List<KalendarEvent>) -> Unit = { _, _ -> },
    takeMeToDate: LocalDate?,
    kalendarDayColors: KalendarDayColors,
    kalendarThemeColor: KalendarThemeColor,
    kalendarHeaderConfig: KalendarHeaderConfig? = null,
    onPreviousClick: (Int, Month) ->  Unit,
    onNextClick: (Int, Month) ->  Unit
) {
    val currentDay = takeMeToDate ?: Clock.System.todayIn(TimeZone.currentSystemDefault())
    val displayedMonth = remember {
        mutableStateOf(currentDay.month)
    }
    val displayedYear = remember {
        mutableStateOf(currentDay.year)
    }
    val currentMonth = displayedMonth.value
    val currentYear = displayedYear.value

    val daysInMonth = currentMonth.minLength()
    val monthValue =
        if (currentMonth.value.toString().length == 1) "0" + currentMonth.value.toString() else currentMonth.value.toString()
    val startDayOfMonth = "$currentYear-$monthValue-01".toLocalDate()
    val firstDayOfMonth = startDayOfMonth.dayOfWeek
    val selectedKalendarDate = remember { mutableStateOf(currentDay) }
    val newKalenderHeaderConfig = KalendarHeaderConfig(
        KalendarTextConfig(
            kalendarTextColor = KalendarTextColor(
                kalendarThemeColor.headerTextColor
            ),
            kalendarTextSize = KalendarTextSize.SubTitle
        )
    )

    Column(
        modifier = modifier
            .background(
                color = kalendarThemeColor.backgroundColor
            )
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        KalendarHeader(
            modifier = Modifier.padding(vertical = 12.dp),
            month = displayedMonth.value,
            onPreviousClick = {
                if (displayedMonth.value.value == 1) {
                    displayedYear.value = displayedYear.value.minus(1)
                }
                displayedMonth.value = displayedMonth.value.minus(1)
                onPreviousClick(displayedYear.value, displayedMonth.value)
            },
            onNextClick = {
                if (displayedMonth.value.value == 12) {
                    displayedYear.value = displayedYear.value.plus(1)
                }
                displayedMonth.value = displayedMonth.value.plus(1)
                onNextClick(displayedYear.value, displayedMonth.value)
            },
            year = displayedYear.value,
            kalendarHeaderConfig = kalendarHeaderConfig ?: newKalenderHeaderConfig
        )

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(7),
            content = {
                items(WeekDays) {
                    KalendarNormalText(
                        text = it,
                        fontWeight = FontWeight.Normal,
                        textColor = kalendarDayColors.textColor,
                    )
                }
                items((getInitialDayOfMonth(firstDayOfMonth)..daysInMonth).toList()) {
                    if (it > 0) {
                        val day = getGeneratedDay(it, currentMonth, currentYear)
                        val isCurrentDay = day == currentDay
                        KalendarDay(
                            kalendarDay = day.toKalendarDay(),
                            modifier = Modifier,
                            kalendarEvents = kalendarEvents,
                            isCurrentDay = isCurrentDay,
                            onCurrentDayClick = { kalendarDay, events ->
                                selectedKalendarDate.value = kalendarDay.localDate
                                onCurrentDayClick(kalendarDay, events)
                            },
                            selectedKalendarDay = selectedKalendarDate.value,
                            kalendarDayColors = kalendarDayColors,
                            //dotColor = kalendarThemeColor.headerTextColor,
                            //dayBackgroundColor = kalendarThemeColor.dayBackgroundColor,
                            dotColor = Yellow,
                            dayBackgroundColor = BlueLight,
                        )
                    }
                }
            }
        )
    }
}

private fun getInitialDayOfMonth(firstDayOfMonth: DayOfWeek) = -(firstDayOfMonth.value).minus(2)

private fun getGeneratedDay(day: Int, currentMonth: Month, currentYear: Int): LocalDate {
    val monthValue =
        if (currentMonth.value.toString().length == 1) "0${currentMonth.value}" else currentMonth.value.toString()
    val newDay = if (day.toString().length == 1) "0$day" else day
    return "$currentYear-$monthValue-$newDay".toLocalDate()
}
