package com.example.exchangeapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Interface for API interactions
interface CurrencyApi {
    @GET("convert")
    suspend fun convertCurrency(
        @Query("access_key") apiKey: String, // API key for authentication
        @Query("from") from: String,        // Source currency
        @Query("to") to: String,            // Target currency
        @Query("amount") amount: Double     // Amount to be converted
    ): ConversionResponse                  // Returns the conversion result

    @GET("list")
    suspend fun getSupportedCurrencies(
        @Query("access_key") apiKey: String // API key for authentication
    ): SupportedCurrenciesResponse         // Returns the list of supported currencies

    companion object {
        // Method to create a Retrofit instance for interacting with the API
        fun create(): CurrencyApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.exchangerate.host/") // Base URL of the API
                .addConverterFactory(GsonConverterFactory.create()) // JSON converter
                .build()
            return retrofit.create(CurrencyApi::class.java) // Create API implementation
        }
    }
}

// Data class for conversion response
data class ConversionResponse(
    val result: Double // The result of the currency conversion
)

// Data class for supported currencies response
data class SupportedCurrenciesResponse(
    val success: Boolean, // Indicates whether the request was successful
    val currencies: Map<String, String> // Map of currency codes and their names
)
