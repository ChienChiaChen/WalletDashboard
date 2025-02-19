package com.exebrain.walletdashboard.ui.wallet.data

data class CryptoAssetModel(
    val iconUrl: String,   // 圖的 URL
    val name: String,    // 幣種名稱
    val amount: Double,   // 幣種數量
    val symbol: String,  // 幣種符號 (例如 "BTC", "ETH", "USDT")
    val usdValue: Double  // 換算後的美元價值
)