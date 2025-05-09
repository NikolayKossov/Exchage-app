package com.example.exchangeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exchangeapp.network.CurrencyApi

// Factory for creating CurrencyViewModel instances
class CurrencyViewModelFactory(
    private val currencyApi: CurrencyApi // API instance for data fetching
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the requested ViewModel matches CurrencyViewModel
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyViewModel(currencyApi) as T // Return a new instance of CurrencyViewModel
        }
        throw IllegalArgumentException("Unknown ViewModel class") // Handle unsupported ViewModel classes
    }
}