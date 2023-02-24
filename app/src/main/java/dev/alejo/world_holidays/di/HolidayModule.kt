package dev.alejo.world_holidays.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.alejo.world_holidays.data.database.dao.HolidayNotificationDao
import dev.alejo.world_holidays.data.network.HolidayService
import dev.alejo.world_holidays.data.repositories.HolidayNotificationRepository
import dev.alejo.world_holidays.data.repositories.HolidayRepository
import dev.alejo.world_holidays.data.repositories.HolidayRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HolidayModule {
    @Singleton
    @Provides
    fun provideHolidayRepository(
        service: HolidayService,
        dao: HolidayNotificationDao
    ): HolidayRepository = HolidayRepositoryImpl(service, dao)

    @Singleton
    @Provides
    fun provideHolidayNotificationRepository(
        service: HolidayService,
        dao: HolidayNotificationDao
    ): HolidayNotificationRepository = HolidayRepositoryImpl(service, dao)
}