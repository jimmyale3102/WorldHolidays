package dev.alejo.world_holidays.data.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("name") val name: String
)