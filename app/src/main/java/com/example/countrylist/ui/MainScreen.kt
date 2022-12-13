package com.example.countrylist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.countrylist.R
import com.example.countrylist.models.Country
import com.example.countrylist.models.CountryHeader
import com.example.countrylist.models.CountryItem
import com.example.countrylist.viewmodel.CountriesScreenState
import com.example.countrylist.viewmodel.CountryViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MainScreen(
    viewModel: CountryViewModel = hiltViewModel()
) {
    val screenState: CountriesScreenState by viewModel.countriesScreenState.collectAsStateWithLifecycle()

    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        when(screenState) {
            CountriesScreenState.Error -> {
               item {
                   Box {
                       Text(
                           modifier = Modifier.align(Alignment.TopCenter),
                           text = stringResource(id = R.string.list_fetching_error))
                   }
               }
            }
            CountriesScreenState.Loading -> {
               item {
                   Box {
                       CircularProgressIndicator(modifier = Modifier.align(Alignment.TopCenter))
                   }
               }
            }
            is CountriesScreenState.Success -> {
                items((screenState as CountriesScreenState.Success).data) { item: CountryItem ->
                    if (item is Country) {
                        CountryInfo(country = item)
                    } else {
                        Header((item as CountryHeader).header)
                    }
                }
            }
        }
    }
}

@Composable
fun Header(header: String){
    Text(
        modifier = Modifier.padding(12.dp),
        text = header,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.ExtraBold
    )
}

@Composable
fun CountryInfo(country: Country) {
    val name = country.name ?: return
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = country.code ?: "",
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = country.capital ?: "",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}