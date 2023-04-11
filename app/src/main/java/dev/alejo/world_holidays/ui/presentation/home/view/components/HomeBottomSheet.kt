package dev.alejo.world_holidays.ui.presentation.home.view.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import dev.alejo.world_holidays.core.uitls.DateUtils
import dev.alejo.world_holidays.data.model.HolidayModel
import dev.alejo.world_holidays.ui.theme.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeBottomSheet(
    isLoading: Boolean,
    holidaysList: List<HolidayModel>,
    onYearChanged: (Int) -> Unit,
    bottomSheetContent: @Composable () -> Unit
) {
    var bottomSheetState by rememberSaveable { mutableStateOf(BottomSheetValue.Collapsed) }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(bottomSheetState)
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(
            topStart = XLarge,
            topEnd = XLarge
        ),
        sheetContent = {
            if (holidaysList.isNotEmpty()) {
                PeekContent(isLoading, holidaysList) { year ->
                    bottomSheetState = BottomSheetValue.Expanded
                    onYearChanged(year)
                }
            }
        },
        sheetBackgroundColor = BlueDark,
        drawerElevation = Small,
        sheetPeekHeight = XLarge
    ) {
        bottomSheetContent()
    }
}

@Composable
private fun PeekContent(
    isLoading: Boolean,
    holidaysList: List<HolidayModel>,
    onYearChanged: (Int) -> Unit
) {
    Crossfade(targetState = isLoading) { loadingContent ->
        if (loadingContent) {
            LoadingComponent()
        } else {
            var isListViewSelected by rememberSaveable { mutableStateOf(false) }
            val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
            var holidaysByMonth by rememberSaveable {
                mutableStateOf(
                    getHolidaysByMonth(holidaysList, currentDate.year, currentDate.monthNumber)
                )
            }
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
                    Column(
                        Modifier.scrollable(
                            rememberScrollState(),
                            orientation = Orientation.Vertical
                        )
                    ) {
                        YearHolidaysList(holidaysList = holidaysList)
                    }
                } else {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
                        verticalArrangement = Arrangement.spacedBy(Medium),
                    ) {
                        HolidaysCalendar(holidaysByMonth) { currentYear, currentMonth ->
                            if (currentYear != DateUtils.getYearNumber(holidaysList[0].date)) {
                                onYearChanged(currentYear)
                            } else {
                                holidaysByMonth =
                                    getHolidaysByMonth(holidaysList, currentYear, currentMonth.value)
                            }
                        }
                        MonthHolidaysList(holidaysList = holidaysByMonth)
                    }
                }
            }
        }
    }
}

private fun getHolidaysByMonth(
    holidaysList: List<HolidayModel>,
    currentYear: Int,
    currentMonth: Int
) = holidaysList.filter { holidayItem ->
    DateUtils.getYearNumber(holidayItem.date) == currentYear
        && DateUtils.getMonthNumber(holidayItem.date) == currentMonth
}