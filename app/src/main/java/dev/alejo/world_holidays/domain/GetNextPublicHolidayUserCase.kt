package dev.alejo.world_holidays.domain

import dev.alejo.world_holidays.data.HolidayRepository
import dev.alejo.world_holidays.data.model.HolidayModel
import javax.inject.Inject

class GetNextPublicHolidayUserCase @Inject constructor(
    private val repository: HolidayRepository
) {
    suspend operator fun invoke(): List<HolidayModel> = repository.getNextPublicHoliday()
}