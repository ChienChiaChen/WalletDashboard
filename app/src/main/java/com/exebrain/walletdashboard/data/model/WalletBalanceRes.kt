package com.exebrain.walletdashboard.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WalletBalanceRes(
    val ok: Boolean,
    val warning: String,
    val wallet: List<WalletItem>
)

@Serializable
data class WalletItem(
    val currency: String,
    val amount: Double
)