package dev.alejo.world_holidays.domain

import dev.alejo.world_holidays.data.repositories.HolidayRepository
import javax.inject.Inject

class GetTodayHolidayUseCase @Inject constructor(
    private val repository: HolidayRepository
) {
    suspend operator fun invoke(countryCode: String): Int = repository.getTodayHoliday(countryCode)
}