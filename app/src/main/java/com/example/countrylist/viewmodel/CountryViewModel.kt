package com.example.countrylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrylist.models.CountryItem
import com.example.countrylist.models.CountryHeader
import com.example.countrylist.repository.CountryRepository
import com.example.countrylist.repository.CountryRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class CountriesScreenState {
    data class Success(val data: List<CountryItem>) : CountriesScreenState()
    object Error: CountriesScreenState()
    object Loading: CountriesScreenState()
}

class CountryViewModel() : ViewModel() {

    private val repository: CountryRepository = CountryRepositoryImpl()

    private val _countriesScreenState = MutableStateFlow<CountriesScreenState>(CountriesScreenState.Loading)
    val countriesScreenState = _countriesScreenState.asStateFlow()

    fun fetchCountries() {
        viewModelScope.launch {
            try {
                val countries = repository.getCountries()
                val map = countries.groupBy { it.name?.substring(0, 1) ?: "" }
                val newList = mutableListOf<CountryItem>()
                map.keys.forEach { title ->
                    newList += CountryHeader(title)
                    map[title]?.let { newList.addAll(it) }
                }
                _countriesScreenState.update { CountriesScreenState.Success(newList) }
            } catch (ex: Exception) {
                _countriesScreenState.update { CountriesScreenState.Error }
            }
        }
    }
}