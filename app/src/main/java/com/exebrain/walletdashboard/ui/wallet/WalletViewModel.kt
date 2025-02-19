package com.exebrain.walletdashboard.ui.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exebrain.walletdashboard.data.DataRepository
import com.exebrain.walletdashboard.ui.wallet.data.CryptoAssetModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalletViewModel : ViewModel() {
    private val _balance = MutableStateFlow("0.00")
    val balance: StateFlow<String> = _balance

    private val _assets = MutableStateFlow<List<CryptoAssetModel>>(emptyList())
    val assets: StateFlow<List<CryptoAssetModel>> = _assets

    init {
        loadBalanceAndAssets()
    }

    private fun loadBalanceAndAssets() {
        _balance.value = "1000.00"
        _assets.value = listOf(
            CryptoAssetModel(
                iconUrl = "https://s3-ap-southeast-1.amazonaws.com/monaco-cointrack-production/uploads/coin/colorful_logo/5c1246f55568a400e48ac233/bitcoin.png",
                name = "Bitcoin",
                amount = 0.005,
                symbol = "BTC",
                usdValue = 200.00
            ),
            CryptoAssetModel(
                iconUrl = "https://s3-ap-southeast-1.amazonaws.com/monaco-cointrack-production/uploads/coin/colorful_logo/5c12487d5568a4017c20a993/ethereum.png",
                name = "Ethereum",
                amount = 0.1,
                symbol = "ETH",
                usdValue = 300.00
            ),
            CryptoAssetModel(
                iconUrl = "https://s3-ap-southeast-1.amazonaws.com/monaco-cointrack-production/uploads/coin/colorful_logo/5c12487f5568a4017c20a999/tether.png",
                name = "Tether",
                amount = 100.0,
                symbol = "USDT",
                usdValue = 100.00
            )
        )
    }

    fun loadCurrenciesList() {
        viewModelScope.launch {
            DataRepository.loadCurrenciesList()
        }
    }
}