package dev.alejo.world_holidays.domain

import dev.alejo.world_holidays.core.Response
import dev.alejo.world_holidays.data.model.Country
import dev.alejo.world_holidays.data.repositories.CountryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(): Flow<Response<List<Country>>> =
        repository.getCountriesFromFirestore()
}