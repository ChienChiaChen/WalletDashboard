package com.exebrain.walletdashboard.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRatesRes(
    val ok: Boolean,
    val warning: String,
    val tiers: List<Tier>
)

@Serializable
data class Tier(
    val from_currency: String,
    val to_currency: String,
    val rates: List<Rate>,
    val time_stamp: Long
)


@Serializable
data class Rate(
    val amount: String,
    val rate: String
)