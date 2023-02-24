package dev.alejo.world_holidays.data.repositories

import dev.alejo.world_holidays.domain.model.HolidayNotificationItem

interface HolidayNotificationRepository {
    suspend fun getHolidayNotification(holidayNotificationId: Int): HolidayNotificationItem?
    suspend fun insertHolidayNotification(holidayNotification: HolidayNotificationItem)
    suspend fun removeHolidayNotification(holidayNotificationId: Int)
}