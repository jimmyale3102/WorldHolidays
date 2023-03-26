package dev.alejo.world_holidays.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.alejo.world_holidays.data.network.HolidayService
import dev.alejo.world_holidays.data.repositories.CountryRepository
import dev.alejo.world_holidays.data.repositories.CountryRepositoryImpl
import dev.alejo.world_holidays.data.repositories.HolidayRepository
import dev.alejo.world_holidays.data.repositories.HolidayRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HolidayModule {
    @Singleton
    @Provides
    fun provideHolidayRepository(service: HolidayService): HolidayRepository =
        HolidayRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideCountryRepository(): CountryRepository = CountryRepositoryImpl()
}