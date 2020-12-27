package com.artem.mezh.stock_analyzer.supplier.dto

data class CompanySummary(
        val exDividendDate: ExDividendDate
)

data class ExDividendDate(
        val raw: Long,
        val fmt: String
)