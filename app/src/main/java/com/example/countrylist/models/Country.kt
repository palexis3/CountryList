package com.example.countrylist.models

data class Country(
    val capital: String?,
    val code: String?,
    val currency: Currency?,
    val flag: String?,
    val language: Language?,
    val name: String?,
    val region: String?
): CountryItem

data class Currency(
    val code: String?,
    val name: String?
)

data class Language(
    val code: String?,
    val name: String?
)

data class CountryHeader(
    val header: String
): CountryItem

interface CountryItem