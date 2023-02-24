package dev.alejo.world_holidays.domain

import dev.alejo.world_holidays.data.repositories.HolidayNotificationRepository
import dev.alejo.world_holidays.domain.model.HolidayNotificationItem
import javax.inject.Inject

class InsertHolidayNotificationUseCase @Inject constructor(
    private val repository: HolidayNotificationRepository
) {
    suspend operator fun invoke(holidayNotification: HolidayNotificationItem) {
        repository.insertHolidayNotification(holidayNotification)
    }
}