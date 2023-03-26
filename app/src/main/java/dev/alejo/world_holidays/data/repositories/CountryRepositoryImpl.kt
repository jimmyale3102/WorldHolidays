package dev.alejo.world_holidays.data.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.alejo.world_holidays.core.Constants.Companion.COUNTRY_COLLECTION
import dev.alejo.world_holidays.core.Response
import dev.alejo.world_holidays.data.model.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CountryRepositoryImpl : CountryRepository {

    private val firestoreInstance = Firebase.firestore

    override suspend fun getCountriesFromFirestore(): Flow<Response<List<Country>>> = callbackFlow {
        try {
            val countries = firestoreInstance
                .collection(COUNTRY_COLLECTION)
                .get()
                .await()
            trySend(
                Response.Success(
                    data = countries.toList().map { country ->
                        Country(
                            countryCode = country["countryCode"].toString(),
                            name = country["name"].toString()
                        )
                    }
                )
            ).isSuccess
        } catch(e: Exception) {
            trySend(Response.Error(message= "Example failure")).isSuccess
        } finally {
            close()
        }
    }
}