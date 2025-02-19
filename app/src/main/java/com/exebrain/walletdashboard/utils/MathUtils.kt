package com.exebrain.walletdashboard.utils

import com.exebrain.walletdashboard.data.model.Tier
import java.math.BigDecimal
import java.math.RoundingMode

object MathUtils {

    /**
     * Calculates the USD value.
     * @param balanceAmount The amount of cryptocurrency owned.
     * @param exchangeRateTier The exchange rate data for the cryptocurrency to USD.
     * @return The converted USD amount.
     */
    fun calculateUsdValue(balanceAmount: Double, exchangeRateTier: Tier?): Double {
        return exchangeRateTier?.rates?.firstOrNull()?.let { rateInfo ->
            val rate = rateInfo.rate.toDoubleOrNull() ?: return 0.0
            val unitAmount = rateInfo.amount.toDoubleOrNull() ?: return 1.0

            BigDecimal(rate)
                .divide(BigDecimal(unitAmount), 10, RoundingMode.HALF_UP) // 避免浮點數誤差
                .multiply(BigDecimal(balanceAmount))
                .setScale(2, RoundingMode.HALF_UP)
                .toDouble()
        } ?: 0.0
    }
}