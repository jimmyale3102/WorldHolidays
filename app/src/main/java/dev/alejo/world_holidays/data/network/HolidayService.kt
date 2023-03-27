package dev.alejo.world_holidays.data.network

import dev.alejo.world_holidays.data.model.Country
import dev.alejo.world_holidays.data.model.HolidayModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HolidayService @Inject constructor(
    private val api: HolidayApiClient
) {

    suspend fun getAvailableCountries(): List<Country> = withContext(Dispatchers.IO) {
        api.getAvailableCountries().body() ?: emptyList()
    }

    suspend fun getHolidaysByYear(countryCode: String, year: String): List<HolidayModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getHolidaysByYear(countryCode, year)
            response.body() ?: emptyList()
        }
    }

    suspend fun getNextPublicHoliday(countryCode: String): List<HolidayModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getNextPublicHoliday(countryCode)
            response.body() ?: emptyList()
        }
    }

    suspend fun getTodayHoliday(countryCode: String): Int {
        return withContext(Dispatchers.IO) {
            val response = api.getTodayHoliday(countryCode)
            response.code()
        }
    }

}