package dev.alejo.world_holidays.data.repositories

import dev.alejo.world_holidays.core.Response
import dev.alejo.world_holidays.data.model.Country
import dev.alejo.world_holidays.data.network.HolidayService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val service: HolidayService
) : CountryRepository {

    override suspend fun getCountriesFromFirestore(): Flow<Response<List<Country>>> = callbackFlow {
        val response = service.getAvailableCountries()
        try {
            trySend(
                Response.Success(data = response)
            ).isSuccess
        } catch(e: Exception) {
            trySend(Response.Error(message= "Example failure")).isSuccess
        } finally {
            close()
        }
    }
}