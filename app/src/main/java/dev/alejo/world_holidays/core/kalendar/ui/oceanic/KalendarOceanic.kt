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
package dev.alejo.world_holidays.core.kalendar.ui.oceanic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import dev.alejo.world_holidays.core.kalendar.ui.oceanic.data.getNext7Dates
import dev.alejo.world_holidays.core.kalendar.ui.oceanic.data.getPrevious7Dates
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun KalendarOceanic(
    takeMeToDate: LocalDate?,
    kalendarDayColors: KalendarDayColors,
    kalendarThemeColors: List<KalendarThemeColor>,
    modifier: Modifier = Modifier,
    showWeekDays: Boolean = true,
    kalendarHeaderConfig: KalendarHeaderConfig? = null,
    kalendarEvents: List<KalendarEvent> = emptyList(),
    onCurrentDayClick: (KalendarDay, List<KalendarEvent>) -> Unit = { _, _ -> },
) {
    val currentDay = takeMeToDate ?: Clock.System.todayIn(TimeZone.currentSystemDefault())
    val weekValue = remember { mutableStateOf(currentDay.getNext7Dates()) }
    val month = weekValue.value.first().month
    val year = weekValue.value.first().year
    val selectedKalendarDate = remember { mutableStateOf(currentDay) }

    Column(
        modifier = modifier
            .background(color = kalendarThemeColors[month.value.minus(1)].backgroundColor)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        KalendarHeader(
            modifier = Modifier.fillMaxWidth(),
            month = month,
            year = year,
            onPreviousClick = {
                val firstDayOfDisplayedWeek = weekValue.value.first()
                weekValue.value = firstDayOfDisplayedWeek.getPrevious7Dates()
            },
            onNextClick = {
                val lastDayOfDisplayedWeek = weekValue.value.last().plus(1, DateTimeUnit.DAY)
                weekValue.value = lastDayOfDisplayedWeek.getNext7Dates()
            },
            kalendarHeaderConfig = kalendarHeaderConfig ?: KalendarHeaderConfig(
                kalendarTextConfig = KalendarTextConfig(
                    kalendarTextColor = KalendarTextColor(
                        kalendarThemeColors[
                            month.value.minus(
                                1
                            )
                        ].headerTextColor
                    ),
                    kalendarTextSize = KalendarTextSize.SubTitle
                )
            ),
        )

        Row(modifier = Modifier.wrapContentWidth()) {
            weekValue.value.forEach { localDate ->
                val isCurrentDay = localDate == currentDay
                Column {
                    if (showWeekDays) {
                        KalendarNormalText(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = localDate.dayOfWeek.getDisplayName(
                                TextStyle.FULL, Locale.getDefault()
                            ).take(1),
                            fontWeight = FontWeight.Normal,
                            textColor = kalendarDayColors.textColor
                        )
                    }
                    KalendarDay(
                        modifier = Modifier,
                        isCurrentDay = isCurrentDay,
                        kalendarDay = localDate.toKalendarDay(),
                        kalendarEvents = kalendarEvents.filter { it.date.dayOfMonth == localDate.dayOfMonth },
                        onCurrentDayClick = { kalendarDay, events ->
                            selectedKalendarDate.value = kalendarDay.localDate
                            onCurrentDayClick(kalendarDay, events)
                        },
                        kalendarDayColors = kalendarDayColors,
                        selectedKalendarDay = selectedKalendarDate.value,
                        dotColor = kalendarThemeColors[month.value.minus(1)].headerTextColor,
                        dayBackgroundColor = kalendarThemeColors[month.value.minus(1)].dayBackgroundColor
                    )
                }
            }
        }
    }
}

@Composable
fun KalendarOceanic(
    modifier: Modifier = Modifier,
    showWeekDays: Boolean = true,
    kalendarEvents: List<KalendarEvent> = emptyList(),
    onCurrentDayClick: (KalendarDay, List<KalendarEvent>) -> Unit = { _, _ -> },
    takeMeToDate: LocalDate?,
    kalendarDayColors: KalendarDayColors,
    kalendarThemeColor: KalendarThemeColor,
    kalendarHeaderConfig: KalendarHeaderConfig? = null
) {
    val currentDay = takeMeToDate ?: Clock.System.todayIn(TimeZone.currentSystemDefault())
    val weekValue = remember { mutableStateOf(currentDay.getNext7Dates()) }
    val month = weekValue.value.first().month
    val year = weekValue.value.first().year
    val selectedKalendarDate = remember { mutableStateOf(currentDay) }

    Column(
        modifier = modifier
            .background(color = kalendarThemeColor.backgroundColor)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        KalendarHeader(
            modifier = Modifier.fillMaxWidth(),
            month = month,
            year = year,
            onPreviousClick = {
                val firstDayOfDisplayedWeek = weekValue.value.first()
                weekValue.value = firstDayOfDisplayedWeek.getPrevious7Dates()
            },
            onNextClick = {
                val lastDayOfDisplayedWeek = weekValue.value.last().plus(1, DateTimeUnit.DAY)
                weekValue.value = lastDayOfDisplayedWeek.getNext7Dates()
            },
            kalendarHeaderConfig = kalendarHeaderConfig ?: KalendarHeaderConfig(
                KalendarTextConfig(
                    kalendarTextColor = KalendarTextColor(
                        kalendarThemeColor.headerTextColor
                    ),
                    kalendarTextSize = KalendarTextSize.SubTitle
                )
            )
        )
        Row(modifier = Modifier.wrapContentWidth()) {
            weekValue.value.forEach { localDate ->
                val isCurrentDay = localDate == currentDay
                Column {
                    if (showWeekDays) {
                        KalendarNormalText(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = localDate.dayOfWeek.getDisplayName(
                                TextStyle.FULL, Locale.getDefault()
                            ).take(1),
                            fontWeight = FontWeight.Normal,
                            textColor = kalendarDayColors.textColor
                        )
                    }
                    KalendarDay(
                        kalendarDay = localDate.toKalendarDay(),
                        modifier = Modifier,
                        kalendarEvents = kalendarEvents,
                        isCurrentDay = isCurrentDay,
                        onCurrentDayClick = { kalendarDay, events ->
                            selectedKalendarDate.value = kalendarDay.localDate
                            onCurrentDayClick(kalendarDay, events)
                        },
                        selectedKalendarDay = selectedKalendarDate.value,
                        kalendarDayColors = kalendarDayColors,
                        dotColor = kalendarThemeColor.headerTextColor,
                        dayBackgroundColor = kalendarThemeColor.dayBackgroundColor,
                    )
                }
            }
        }
    }
}
