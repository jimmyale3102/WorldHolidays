package dev.alejo.world_holidays.domain

import dev.alejo.world_holidays.data.model.HolidayModel
import dev.alejo.world_holidays.data.repositories.HolidayRepository
import javax.inject.Inject

class GetNextPublicHolidayUserCase @Inject constructor(
    private val repository: HolidayRepository
) {
    suspend operator fun invoke(countryCode: Int): List<HolidayModel> =
        repository.getNextPublicHoliday(countryCode)
}