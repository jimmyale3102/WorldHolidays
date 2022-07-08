package dev.alejo.world_holidays.domain

import dev.alejo.world_holidays.data.HolidayRepository
import javax.inject.Inject

class GetTodayHolidayUseCase @Inject constructor(
    private val repository: HolidayRepository
) {
    suspend operator fun invoke(): Int = repository.getTodayHoliday()
}