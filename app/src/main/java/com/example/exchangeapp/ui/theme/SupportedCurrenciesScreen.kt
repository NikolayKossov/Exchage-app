package com.example.exchangeapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exchangeapp.viewmodel.CurrencyViewModel

@Composable
fun SupportedCurrenciesScreen(
    viewModel: CurrencyViewModel, // ViewModel to fetch currency data
    onBack: () -> Unit // Callback for navigating back
) {
    val supportedCurrencies by viewModel.supportedCurrencies.collectAsState()

    // Load currencies on screen display
    LaunchedEffect(Unit) {
        viewModel.fetchSupportedCurrencies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize() // Occupies full screen space
            .padding(16.dp), // Padding around the content
        verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between elements
    ) {
        TopAppBar(
            title = { Text("Supported Currencies") }, // Title of the screen
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back") // Back button
                }
            }
        )

        // Display the list of currencies or a loading message
        if (supportedCurrencies != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), // Allows scrolling if the list is long
                verticalArrangement = Arrangement.spacedBy(8.dp) // Space between list items
            ) {
                items(supportedCurrencies!!.entries.toList()) { (code, name) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth() // Each card spans full width
                            .padding(8.dp), // Padding around each card
                        elevation = 4.dp // Shadow effect
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = code, style = MaterialTheme.typography.h6) // Currency code (e.g., USD)
                            Spacer(modifier = Modifier.height(4.dp)) // Space between code and name
                            Text(text = name, style = MaterialTheme.typography.body2) // Currency name (e.g., Dollar)
                        }
                    }
                }
            }
        } else {
            Text(
                text = "Loading supported currencies...", // Shown while data is being fetched
                style = MaterialTheme.typography.body2, // Style for body text
                modifier = Modifier.padding(8.dp) // Padding around the text
            )
        }
    }
}
