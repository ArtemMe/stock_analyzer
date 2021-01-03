package com.artem.mezh.stock_analyzer.repository.entity

import java.math.BigDecimal
import java.time.LocalDateTime

data class ShareEntity(
        val symbol: String,
        val exDividend: LocalDateTime,
        val amount: Int? = null
)
