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
package dev.alejo.world_holidays.core.kalendar

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.alejo.world_holidays.core.kalendar.ui.oceanic.KalendarOceanic
import dev.alejo.world_holidays.core.kalendar.color.KalendarColors
import dev.alejo.world_holidays.core.kalendar.color.KalendarThemeColor
import dev.alejo.world_holidays.core.kalendar.component.day.config.KalendarDayColors
import dev.alejo.world_holidays.core.kalendar.component.day.config.KalendarDayDefaultColors
import dev.alejo.world_holidays.core.kalendar.component.header.config.KalendarHeaderConfig
import dev.alejo.world_holidays.core.kalendar.model.KalendarDay
import dev.alejo.world_holidays.core.kalendar.model.KalendarEvent
import dev.alejo.world_holidays.core.kalendar.model.KalendarType
import dev.alejo.world_holidays.core.kalendar.ui.firey.KalendarFirey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

@Composable
fun Kalendar(
    modifier: Modifier = Modifier,
    kalendarType: KalendarType = KalendarType.Oceanic(true),
    kalendarEvents: List<KalendarEvent> = emptyList(),
    kalendarThemeColors: List<KalendarThemeColor> = KalendarColors.defaultColors(),
    onCurrentDayClick: (KalendarDay, List<KalendarEvent>) -> Unit = { _, _ -> },
    kalendarDayColors: KalendarDayColors = KalendarDayDefaultColors.defaultColors(),
    kalendarHeaderConfig: KalendarHeaderConfig? = null,
    takeMeToDate: LocalDate? = null,
    onPreviousClick: (Int, Month) -> Unit = {_, _ -> },
    onNextClick: (Int, Month) -> Unit = {_, _ -> }
) {
    if (kalendarThemeColors.isEmpty() || kalendarThemeColors.count() < 12) throw Exception("KalendarThemeColor cannot be null or less than 12, If you want to use same color accors months pass kalendarThemeColor = KalendarThemeColor(values)")

    when (kalendarType) {
        is KalendarType.Oceanic -> KalendarOceanic(
            modifier = modifier.wrapContentHeight(),
            kalendarEvents = kalendarEvents,
            onCurrentDayClick = onCurrentDayClick,
            kalendarDayColors = kalendarDayColors,
            kalendarThemeColors = kalendarThemeColors,
            takeMeToDate = takeMeToDate,
            kalendarHeaderConfig = kalendarHeaderConfig,
            showWeekDays = kalendarType.showWeekDays

        )
        KalendarType.Firey -> {
            KalendarFirey(
                modifier = modifier.wrapContentHeight(),
                kalendarEvents = kalendarEvents,
                onCurrentDayClick = onCurrentDayClick,
                kalendarDayColors = kalendarDayColors,
                kalendarThemeColors = kalendarThemeColors,
                takeMeToDate = takeMeToDate,
                kalendarHeaderConfig = kalendarHeaderConfig,
                onPreviousClick = { currentYear, currentMonth ->
                    onPreviousClick(
                        currentYear,
                        currentMonth
                    )
                },
                onNextClick = { currentYear, currentMonth ->
                    onNextClick(
                        currentYear,
                        currentMonth
                    )
                }
            )
        }
    }
}

@Composable
fun Kalendar(
    modifier: Modifier = Modifier,
    kalendarThemeColor: KalendarThemeColor,
    kalendarType: KalendarType = KalendarType.Oceanic(true),
    kalendarEvents: List<KalendarEvent> = emptyList(),
    onCurrentDayClick: (KalendarDay, List<KalendarEvent>) -> Unit = { _, _ -> },
    kalendarDayColors: KalendarDayColors = KalendarDayDefaultColors.defaultColors(),
    kalendarHeaderConfig: KalendarHeaderConfig? = null,
    takeMeToDate: LocalDate? = null,
    onPreviousClick: (Int, Month) -> Unit = {_, _ -> },
    onNextClick: (Int, Month) -> Unit = {_, _ -> }
) {
    when (kalendarType) {
        is KalendarType.Oceanic -> KalendarOceanic(
            modifier = modifier.wrapContentHeight(),
            kalendarEvents = kalendarEvents,
            onCurrentDayClick = onCurrentDayClick,
            kalendarDayColors = kalendarDayColors,
            kalendarThemeColor = kalendarThemeColor,
            takeMeToDate = takeMeToDate,
            kalendarHeaderConfig = kalendarHeaderConfig,
            showWeekDays = kalendarType.showWeekDays
        )
        KalendarType.Firey -> {
            KalendarFirey(
                modifier = modifier.wrapContentHeight(),
                kalendarEvents = kalendarEvents,
                onCurrentDayClick = onCurrentDayClick,
                kalendarDayColors = kalendarDayColors,
                kalendarThemeColor = kalendarThemeColor,
                takeMeToDate = takeMeToDate,
                kalendarHeaderConfig = kalendarHeaderConfig,
                onPreviousClick = { currentYear, currentMonth ->
                    onPreviousClick(
                        currentYear,
                        currentMonth
                    )
                },
                onNextClick = { currentYear, currentMonth ->
                    onNextClick(
                        currentYear,
                        currentMonth
                    )
                }
            )
        }
    }
}
