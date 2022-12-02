package com.example.countrylist.networking

import com.example.countrylist.models.CountryListResponse

interface CountryApi {
    suspend fun getCountries(): CountryListResponse
}