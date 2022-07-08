package dev.alejo.world_holidays.core.extensions

import dev.alejo.world_holidays.data.database.entities.HolidayNotificationEntity
import dev.alejo.world_holidays.domain.model.HolidayNotificationItem

fun HolidayNotificationEntity.toDomain() = HolidayNotificationItem(id)