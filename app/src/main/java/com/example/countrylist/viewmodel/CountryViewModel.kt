package com.example.countrylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrylist.models.Country
import com.example.countrylist.repository.CountryRepository
import com.example.countrylist.repository.CountryRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class CountriesScreenState {
    data class Success(val data: List<Country>) : CountriesScreenState()
    object Error: CountriesScreenState()
    object Loading: CountriesScreenState()
}

class CountryViewModel() : ViewModel() {

    private val repository: CountryRepository = CountryRepositoryImpl()
    private val _screenState = MutableStateFlow<CountriesScreenState>(CountriesScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    fun fetchCountries() {
        viewModelScope.launch {
            try {
                val items = repository.getCountries()
                _screenState.update { CountriesScreenState.Success(items) }
            } catch (ex: Exception) {
                _screenState.update { CountriesScreenState.Error }
            }
        }
    }
}