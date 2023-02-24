package dev.alejo.world_holidays.domain

import dev.alejo.world_holidays.data.model.HolidayModel
import dev.alejo.world_holidays.data.repositories.HolidayRepository
import javax.inject.Inject

class GetHolidayUseCase @Inject constructor(
    private val repository: HolidayRepository
) {
    suspend operator fun invoke(countryCode: Int, year: String): List<HolidayModel> =
        repository.getHolidaysByYear(countryCode, year)
}