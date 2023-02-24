package dev.alejo.world_holidays.data.repositories

import dev.alejo.world_holidays.data.model.HolidayModel

interface HolidayRepository {
    suspend fun getHolidaysByYear(countryCode: Int, year: String): List<HolidayModel>
    suspend fun getNextPublicHoliday(countryCode: Int): List<HolidayModel>
    suspend fun getTodayHoliday(countryCode: Int): Int
}