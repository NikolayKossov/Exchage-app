package com.example.exchangeapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exchangeapp.viewmodel.CurrencyViewModel

// Main screen of the app
@Composable
fun MainScreen(
    viewModel: CurrencyViewModel // Handles the app's logic
) {
    // Tracks whether to show the supported currencies screen
    var showSupportedCurrencies by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exchange App") } // App title
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize() // Takes up the full screen
        ) {
            // Switch between the main content and the supported currencies screen
            if (showSupportedCurrencies) {
                SupportedCurrenciesScreen(
                    viewModel = viewModel,
                    onBack = { showSupportedCurrencies = false } // Return to the main screen
                )
            } else {
                MainContent(
                    viewModel = viewModel,
                    onOpenSupportedCurrencies = { showSupportedCurrencies = true } // Open supported currencies screen
                )
            }
        }
    }
}

// Main content for currency conversion
@Composable
fun MainContent(
    viewModel: CurrencyViewModel, // ViewModel for handling conversion logic
    onOpenSupportedCurrencies: () -> Unit // Opens the supported currencies screen
) {
    // User input states
    var fromCurrency by remember { mutableStateOf("") }
    var toCurrency by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    // Conversion result from the ViewModel
    val result by viewModel.conversionResult.collectAsState(initial = null)

    // Tracks errors in user input
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize() // Fills the screen
            .padding(16.dp), // Adds padding around the content
        verticalArrangement = Arrangement.spacedBy(8.dp) // Space between components
    ) {
        // Input for the source currency
        TextField(
            value = fromCurrency,
            onValueChange = { fromCurrency = it },
            label = { Text("From Currency (e.g., USD)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input for the target currency
        TextField(
            value = toCurrency,
            onValueChange = { toCurrency = it },
            label = { Text("To Currency (e.g., EUR)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input for the amount to convert
        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        // Button to trigger conversion
        Button(
            onClick = {
                val amountValue = amount.toDoubleOrNull()
                if (fromCurrency.isNotBlank() && toCurrency.isNotBlank() && amountValue != null) {
                    viewModel.convertCurrency(fromCurrency, toCurrency, amountValue)
                } else {
                    errorMessage = "Please enter valid data."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Convert")
        }

        // Show conversion result if available
        if (result?.result != null) {
            Text(
                text = "Converted Amount: ${"%.2f".format(result?.result)}",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Show error message if input is invalid
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Adds some space between components
        Spacer(modifier = Modifier.height(16.dp))

        // Button to navigate to the supported currencies screen
        Button(onClick = { onOpenSupportedCurrencies() }) {
            Text("Show Supported Currencies")
        }
    }
}
