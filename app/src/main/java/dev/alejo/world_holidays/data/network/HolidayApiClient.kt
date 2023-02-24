package dev.alejo.world_holidays.data.network

import dev.alejo.world_holidays.data.model.HolidayModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HolidayApiClient {

    @GET("IsTodayPublicHoliday/{countryCode}")
    suspend fun getTodayHoliday(@Path("countryCode") countryCode: String): Response<Void>

    @GET("PublicHolidays/{year}/{countryCode}")
    suspend fun getHolidaysByYear(
        @Path("countryCode") countryCode: String,
        @Path("year") year: String
    ): Response<List<HolidayModel>>

    @GET("NextPublicHolidays/{countryCode}")
    suspend fun getNextPublicHoliday(@Path("countryCode") countryCode: String): Response<List<HolidayModel>>

}