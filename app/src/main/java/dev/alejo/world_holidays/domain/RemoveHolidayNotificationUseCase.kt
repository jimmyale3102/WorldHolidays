package dev.alejo.world_holidays.domain

import dev.alejo.world_holidays.data.repositories.HolidayNotificationRepository
import javax.inject.Inject

class RemoveHolidayNotificationUseCase @Inject constructor(
    private val repository: HolidayNotificationRepository
) {
    suspend operator fun invoke(holidayNotificationId: Int) {
        repository.removeHolidayNotification(holidayNotificationId)
    }
}