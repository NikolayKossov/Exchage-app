package com.example.exchangeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.exchangeapp.viewmodel.CurrencyViewModel
import com.example.exchangeapp.viewmodel.CurrencyViewModelFactory
import com.example.exchangeapp.network.CurrencyApi
import com.example.exchangeapp.ui.theme.MainScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create an instance of CurrencyApi for fetching data
        val currencyApi = CurrencyApi.create()

        // Initialize the ViewModelFactory with the API instance
        val viewModelFactory = CurrencyViewModelFactory(currencyApi)

        // Use the factory to create an instance of CurrencyViewModel
        val viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyViewModel::class.java]

        // Set the Compose content to dis  play the MainScreen
        setContent {
            MainScreen(viewModel = viewModel) // Pass the ViewModel to the screen
        }
    }
}
