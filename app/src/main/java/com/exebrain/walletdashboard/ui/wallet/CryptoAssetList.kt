package com.exebrain.walletdashboard.ui.wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.exebrain.walletdashboard.ui.wallet.data.CryptoAssetModel

@Composable
fun CryptoAssetItem(asset: CryptoAssetModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(asset.iconUrl)
                .crossfade(true)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = "${asset.name} Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = asset.name,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.DarkGray
            ),
            modifier = Modifier.weight(1f)
        )

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${asset.amount} ${asset.symbol}",
                style = TextStyle(color = Color.DarkGray)
            )
            Text(
                text = "$${asset.usdValue}",
                style = TextStyle(color = Color.Gray, fontSize = 12.sp)
            )
        }
    }
}

@Composable
fun CryptoAssetList(assets: List<CryptoAssetModel>) {
    LazyColumn {
        items(assets) { asset ->
            CryptoAssetItem(asset = asset)
            HorizontalDivider()

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCryptoAssetList() {
    val sampleAssets = listOf(
        CryptoAssetModel(
            iconUrl = "https://cryptoicons",
            name = "Bitcoin",
            amount = 0.005,
            symbol = "BTC",
            usdValue = 200.00
        ),
        CryptoAssetModel(
            iconUrl = "https://cryptoicons",
            name = "Ethereum",
            amount = 0.1,
            symbol = "ETH",
            usdValue = 300.00
        ),
        CryptoAssetModel(
            iconUrl = "https://cryptoicons",
            name = "Tether",
            amount = 100.0,
            symbol = "USDT",
            usdValue = 100.00
        )
    )
    CryptoAssetList(assets = sampleAssets)
}