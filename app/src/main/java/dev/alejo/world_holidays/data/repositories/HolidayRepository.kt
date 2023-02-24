package dev.alejo.world_holidays.data.repositories

import dev.alejo.world_holidays.data.model.HolidayModel

interface HolidayRepository {
    suspend fun getHolidaysByYear(countryCode: String, year: String): List<HolidayModel>
    suspend fun getNextPublicHoliday(countryCode: String): List<HolidayModel>
    suspend fun getTodayHoliday(countryCode: String): Int
}