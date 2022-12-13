package com.example.countrylist.repository

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    fun bindRepositoryImpl(repositoryImpl: CountryRepositoryImpl): CountryRepository
}