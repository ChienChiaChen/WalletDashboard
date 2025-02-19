package com.exebrain.walletdashboard.ui.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exebrain.walletdashboard.data.DataRepository
import com.exebrain.walletdashboard.ui.wallet.data.CryptoAssetModel
import com.exebrain.walletdashboard.utils.MathUtils
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class WalletViewModel : ViewModel() {
    private val currenciesFlow = DataRepository.loadCurrenciesList()
    private val exchangeRatesFlow = DataRepository.loadExchangeRates()
    private val walletBalanceFlow = DataRepository.loadWalletBalance()

    val walletUiState: StateFlow<WalletUiState> = combine(
        currenciesFlow,
        exchangeRatesFlow,
        walletBalanceFlow
    ) { currencies, exchangeRates, walletBalance ->
        // Calculate the corresponding USD value for each asset and convert it into a CryptoAssetModel
        val cryptoAssets = walletBalance?.wallet?.mapNotNull { walletItem ->
            val currencyInfo =
                currencies?.currencies?.firstOrNull { it.code == walletItem.currency }
            val exchangeRateTier = exchangeRates?.tiers?.firstOrNull {
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

        WalletUiState(
            balance = String.format("%.2f", totalUsdBalance),
            assets = cryptoAssets
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, WalletUiState("", emptyList()))
}