package dev.alejo.world_holidays.data.repositories

import dev.alejo.world_holidays.data.model.HolidayModel
import dev.alejo.world_holidays.data.network.HolidayService
import javax.inject.Inject

class HolidayRepositoryImpl @Inject constructor(
    private val service: HolidayService
) : HolidayRepository {

    override suspend fun getHolidaysByYear(countryCode: String, year: String): List<HolidayModel> =
        service.getHolidaysByYear(countryCode, year)

    override suspend fun getNextPublicHoliday(countryCode: String): List<HolidayModel> =
        service.getNextPublicHoliday(countryCode)

    override suspend fun getTodayHoliday(countryCode: String): Int =
        service.getTodayHoliday(countryCode)
}