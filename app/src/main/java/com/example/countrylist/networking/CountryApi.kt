package com.example.countrylist.networking

import com.example.countrylist.models.Country
import retrofit2.http.GET

interface CountryApi {

    @GET(".")
    suspend fun getCountries(): List<Country>
}