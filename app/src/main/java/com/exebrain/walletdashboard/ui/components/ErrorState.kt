package com.exebrain.walletdashboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorState(errorMessage: String, onRetryClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 150.dp)
        )

        Button(
            onClick = onRetryClick,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Retry")
        }
    }
}