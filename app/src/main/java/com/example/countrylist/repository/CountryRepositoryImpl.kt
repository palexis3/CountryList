package com.example.countrylist.repository

import com.example.countrylist.models.Country
import com.example.countrylist.networking.NetworkModule

class CountryRepositoryImpl: CountryRepository {

    private val api = NetworkModule.provideCountryApi()

    override suspend fun getCountries(): List<Country> {
        return api.getCountries()
    }
}