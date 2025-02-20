package com.exebrain.walletdashboard.data

import android.util.Log
import com.exebrain.walletdashboard.data.model.CurrenciesListRes
import com.exebrain.walletdashboard.data.model.ExchangeRatesRes
import com.exebrain.walletdashboard.data.model.WalletBalanceRes
import com.exebrain.walletdashboard.utils.JsonUtils
import com.exebrain.walletdashboard.utils.NetUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object DataRepository {

    private inline fun <reified T> loadDataFromJson(
        fileName: String,
        defaultValue: T
    ): Flow<T> = flow {
        Log.d("jason", "Loading: $fileName")
        val result = if (!NetUtils.isNetworkAvailable()) {
            defaultValue
        } else {
            JsonUtils.parseJsonFromFile<T>(fileName) ?: defaultValue
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun loadCurrenciesList(): Flow<CurrenciesListRes> =
        loadDataFromJson("currencies.json", CurrenciesListRes(ok = false, currencies = emptyList(), total = 0))

    fun loadExchangeRates(): Flow<ExchangeRatesRes> =
        loadDataFromJson("exchange_rates.json", ExchangeRatesRes(ok = false, warning = "", tiers = emptyList()))

    fun loadWalletBalance(): Flow<WalletBalanceRes> =
        loadDataFromJson("wallet_balance.json", WalletBalanceRes(ok = false, warning = "", wallet = emptyList()))
}