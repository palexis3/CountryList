package com.example.countrylist.repository

import com.example.countrylist.models.Country

interface CountryRepository {
    suspend fun getCountries(): List<Country>
}