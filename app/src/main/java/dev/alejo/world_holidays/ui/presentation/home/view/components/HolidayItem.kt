package dev.alejo.world_holidays.ui.presentation.home.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.alejo.world_holidays.core.uitls.DateUtils
import dev.alejo.world_holidays.data.model.HolidayModel
import dev.alejo.world_holidays.ui.theme.*

@Composable
fun HolidayItem(holidayItem: HolidayModel) {
    val dateFromString = DateUtils.getDateFromString(holidayItem.date)
    val day = dateFromString?.let { DateUtils.getDayFromDate(it) } ?: "-"
    val dayName = dateFromString?.let { DateUtils.getDayNameFromDate(it) } ?: "-"
    Card(
        modifier = Modifier.fillMaxWidth().clickable {  },
        colors = CardDefaults.cardColors(containerColor = BlueKing),
        shape = RoundedCornerShape(Medium)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(Medium)) {
            Divider(
                Modifier
                    .height(MediumLarge)
                    .width(MediumSmall)
                    .background(Yellow)
                    .padding(horizontal = Medium)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = Medium)
            ) {
                Text(
                    text = dayName,
                    color = WhiteCake,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = day,
                    color = WhiteCake,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Text(
                text = holidayItem.name,
                color = WhiteCake,
                fontSize = 16.sp,
                modifier = Modifier.padding(end = Medium)
            )
        }
    }
}

@Composable
fun PrePreview() {
    HolidayItem(holidayItem = HolidayModel("", "", "", true, true, null, emptyList()))
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    PrePreview()
}