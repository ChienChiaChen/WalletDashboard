package com.exebrain.walletdashboard.data

import com.exebrain.walletdashboard.data.model.CurrenciesListRes
import com.exebrain.walletdashboard.data.model.ExchangeRatesRes
import com.exebrain.walletdashboard.data.model.WalletBalanceRes
import com.exebrain.walletdashboard.utils.JsonUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object DataRepository {

    fun loadCurrenciesList(): Flow<CurrenciesListRes?> = flow {
        val result = JsonUtils.parseJsonFromFile<CurrenciesListRes>("currencies.json")
        emit(result)
    }

    fun loadExchangeRates(): Flow<ExchangeRatesRes?> = flow {
        val result = JsonUtils.parseJsonFromFile<ExchangeRatesRes>("exchange_rates.json")
        emit(result)
    }

    fun loadWalletBalance(): Flow<WalletBalanceRes?> = flow {
        val result = JsonUtils.parseJsonFromFile<WalletBalanceRes>("wallet_balance.json")
        emit(result)
    }

}