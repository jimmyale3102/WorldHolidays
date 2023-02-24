package dev.alejo.world_holidays.data.repositories

import dev.alejo.world_holidays.core.extensions.toDatabase
import dev.alejo.world_holidays.core.extensions.toDomain
import dev.alejo.world_holidays.data.database.dao.HolidayNotificationDao
import dev.alejo.world_holidays.data.model.HolidayModel
import dev.alejo.world_holidays.data.network.HolidayService
import dev.alejo.world_holidays.domain.model.HolidayNotificationItem
import javax.inject.Inject

class HolidayRepositoryImpl @Inject constructor(
    private val service: HolidayService,
    private val dao: HolidayNotificationDao
) : HolidayRepository, HolidayNotificationRepository {

    override suspend fun getHolidaysByYear(countryCode: String, year: String): List<HolidayModel> =
        service.getHolidaysByYear(countryCode, year)

    override suspend fun getNextPublicHoliday(countryCode: String): List<HolidayModel> =
        service.getNextPublicHoliday(countryCode)

    override suspend fun getTodayHoliday(countryCode: String): Int =
        service.getTodayHoliday(countryCode)

    override suspend fun getHolidayNotification(holidayNotificationId: Int): HolidayNotificationItem? =
        dao.getHolidayNotification(holidayNotificationId)?.toDomain()

    override suspend fun insertHolidayNotification(holidayNotification: HolidayNotificationItem) {
        dao.insertHolidayNotification(holidayNotification.toDatabase())
    }

    override suspend fun removeHolidayNotification(holidayNotificationId: Int) {
        dao.removeHolidayNotification(holidayNotificationId)
    }
}