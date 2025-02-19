package com.exebrain.walletdashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.exebrain.walletdashboard.ui.theme.WalletDashboardTheme
import com.exebrain.walletdashboard.ui.wallet.WalletScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WalletDashboardTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    WalletScreen(innerPadding = innerPadding)
                }
            }
        }
    }
}
