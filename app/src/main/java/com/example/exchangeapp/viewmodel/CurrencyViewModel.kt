package com.example.exchangeapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeapp.BuildConfig
import com.example.exchangeapp.network.ConversionResponse
import com.example.exchangeapp.network.CurrencyApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel for managing currency conversion logic
class CurrencyViewModel(private val currencyApi: CurrencyApi) : ViewModel() {

    // Holds the result of a currency conversion
    private val _conversionResult = MutableStateFlow<ConversionResponse?>(null)
    val conversionResult: StateFlow<ConversionResponse?> = _conversionResult // Publicly exposed for UI

    // Holds the list of supported currencies
    private val _supportedCurrencies = MutableStateFlow<Map<String, String>?>(null)
    val supportedCurrencies: StateFlow<Map<String, String>?> = _supportedCurrencies // Publicly exposed for UI

    // Performs currency conversion and updates the result state
    fun convertCurrency(from: String, to: String, amount: Double) {
        viewModelScope.launch {
            try {
                val response = currencyApi.convertCurrency(BuildConfig.API_KEY, from, to, amount)
                _conversionResult.value = response // Update state with the conversion result
            } catch (e: Exception) {
                e.printStackTrace() // Log the exception
                _conversionResult.value = null // Reset the state on error
            }
        }
    }

    // Fetches the list of supported currencies and updates the state
    fun fetchSupportedCurrencies() {
        viewModelScope.launch {
            try {
                val response = currencyApi.getSupportedCurrencies(BuildConfig.API_KEY)
                if (response.success) {
                    _supportedCurrencies.value = response.currencies // Update state with the list of currencies
                } else {
                    _supportedCurrencies.value = null // Handle error response
                }
            } catch (e: Exception) {
                e.printStackTrace() // Log the exception
                _supportedCurrencies.value = null // Reset the state on error
            }
        }
    }
}
