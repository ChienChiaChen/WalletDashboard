package com.exebrain.walletdashboard.data

import com.exebrain.walletdashboard.data.model.CurrenciesListRes
import com.exebrain.walletdashboard.data.model.ExchangeRatesRes
import com.exebrain.walletdashboard.data.model.WalletBalanceRes
import com.exebrain.walletdashboard.utils.JsonUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object DataRepository {

    fun loadCurrenciesList(): Flow<CurrenciesListRes> = flow {
        val result = JsonUtils.parseJsonFromFile<CurrenciesListRes>("currencies.json")
            ?: CurrenciesListRes(
                ok = false,
                currencies = emptyList(),
                total = 0
            )
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun loadExchangeRates(): Flow<ExchangeRatesRes> = flow {
        val result = JsonUtils.parseJsonFromFile<ExchangeRatesRes>("exchange_rates.json")
            ?: ExchangeRatesRes(
                ok = false,
                warning = "",
                tiers = emptyList()
            )
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun loadWalletBalance(): Flow<WalletBalanceRes> = flow {
        val result = JsonUtils.parseJsonFromFile<WalletBalanceRes>("wallet_balance.json")
            ?: WalletBalanceRes(
                ok = false,
                warning = "",
                wallet = emptyList()
            )
        emit(result)
    }.flowOn(Dispatchers.IO)
}