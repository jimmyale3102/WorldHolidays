package dev.alejo.world_holidays.data.repositories

import dev.alejo.world_holidays.core.Response
import dev.alejo.world_holidays.data.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    suspend fun getCountriesFromFirestore(): Flow<Response<List<Country>>>
}