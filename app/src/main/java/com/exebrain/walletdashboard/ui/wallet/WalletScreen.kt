package com.exebrain.walletdashboard.ui.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.exebrain.walletdashboard.ui.components.ErrorState


@Composable
fun WalletScreen(
    viewModel: WalletViewModel = viewModel(),
    innerPadding: PaddingValues
) {
    val walletUiState by viewModel.walletUiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color(0xFF003459))
    ) {
        BalanceDisplay(walletUiState.balance)

        walletUiState.errorMessage?.takeIf { it.isNotBlank() }?.let { errMsg->
            ErrorState(
                errorMessage = errMsg,
                onRetryClick = { viewModel.onRetry() })
        }?: Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 8.dp),
            color = Color.White
        ) {
            CryptoAssetList(assets = walletUiState.assets)
        }
    }
}

