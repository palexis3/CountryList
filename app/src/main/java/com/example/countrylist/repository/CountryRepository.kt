package com.example.countrylist.repository

import com.example.countrylist.models.Country
import com.example.countrylist.networking.NetworkModule

interface CountryRepository {
    suspend fun getCountries(): List<Country>
}

class CountryRepositoryImpl: CountryRepository {

    private val api = NetworkModule.provideCountryApi()

    override suspend fun getCountries(): List<Country> {
        return api.getCountries()
    }
}