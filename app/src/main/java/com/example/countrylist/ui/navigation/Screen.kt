package com.example.countrylist.ui.navigation

sealed class Screen(val route: String) {
    object List : Screen("List")
}
