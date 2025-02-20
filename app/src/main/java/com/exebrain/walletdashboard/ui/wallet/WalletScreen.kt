package com.exebrain.walletdashboard.ui.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.exebrain.walletdashboard.ui.components.ErrorState


@Composable
fun WalletScreen(
    viewModel: WalletViewModel = viewModel(),
    innerPadding: PaddingValues
) {
    val walletUiState by viewModel.walletUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color(0xFF003459))
    ) {
        BalanceDisplay(walletUiState.balance)

        if (walletUiState.errorMessage.isNullOrBlank()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 8.dp),
                color = Color.White
            ) {
                CryptoAssetList(assets = walletUiState.assets)
            }
        } else {
            ErrorState(
                errorMessage = walletUiState.errorMessage!!,
                onRetryClick = { viewModel.onRetry() })
        }
    }
}

