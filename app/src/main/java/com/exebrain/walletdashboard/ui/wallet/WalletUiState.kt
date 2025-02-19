package com.exebrain.walletdashboard.ui.wallet

import com.exebrain.walletdashboard.ui.wallet.data.CryptoAssetModel

data class WalletUiState(
    val balance: String,
    val assets: List<CryptoAssetModel>
)