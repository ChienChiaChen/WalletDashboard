package com.exebrain.walletdashboard.ui.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exebrain.walletdashboard.data.DataRepository
import com.exebrain.walletdashboard.data.model.CurrenciesListRes
import com.exebrain.walletdashboard.data.model.ExchangeRatesRes
import com.exebrain.walletdashboard.data.model.WalletBalanceRes
import com.exebrain.walletdashboard.ui.wallet.data.CryptoAssetModel
import com.exebrain.walletdashboard.utils.MathUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class WalletViewModel : ViewModel() {
    private val currenciesFlow = DataRepository.loadCurrenciesList()
    private val exchangeRatesFlow = DataRepository.loadExchangeRates()
    private val walletBalanceFlow = DataRepository.loadWalletBalance()

    private val _onRetry = MutableStateFlow<Int>(0)

    val walletUiState: StateFlow<WalletUiState> = _onRetry.flatMapLatest {
        combine(
            currenciesFlow,
            exchangeRatesFlow,
            walletBalanceFlow,
        ) { currencies, exchangeRates, walletBalance ->
            // If any data source has ok set to false, return an error state.
            mapToWalletUiState(currencies, exchangeRates, walletBalance)
        }
    }.distinctUntilChanged()
        .flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            WalletUiState("0.00", emptyList())
        )

    private fun mapToWalletUiState(
        currencies: CurrenciesListRes,
        exchangeRates: ExchangeRatesRes,
        walletBalance: WalletBalanceRes
    ): WalletUiState {
        if (!currencies.ok || !exchangeRates.ok || !walletBalance.ok) {
            return WalletUiState(
                balance = "0.00",
                assets = emptyList(),
                errorMessage = "Loading failed"
            )
        }

        // Calculate the corresponding USD value for each asset and convert it into a CryptoAssetModel
        val cryptoAssets = walletBalance.wallet.mapNotNull { walletItem ->
            val currencyInfo =
                currencies.currencies.firstOrNull { it.code == walletItem.currency }
            val exchangeRateTier = exchangeRates.tiers.firstOrNull {
                it.from_currency == walletItem.currency && it.to_currency == "USD"
            }
            val usdValue = MathUtils.calculateUsdValue(walletItem.amount, exchangeRateTier)
            currencyInfo?.let {
                CryptoAssetModel(
                    iconUrl = it.colorful_image_url,
                    name = it.name,
                    symbol = it.symbol,
                    amount = walletItem.amount,
                    usdValue = usdValue
                )
            }
        } ?: emptyList()

        // Calculate the total USD value of the entire wallet
        val totalUsdBalance = cryptoAssets.sumOf { it.usdValue }

        return WalletUiState(
            balance = String.format("%.2f", totalUsdBalance),
            assets = cryptoAssets,
            errorMessage = ""
        )
    }
    fun onRetry() {
        _onRetry.value++
    }
}