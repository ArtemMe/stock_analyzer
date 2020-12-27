package com.artem.mezh.stock_analyzer.client

data class ChartInfoParams(
        val symbol: String,
        val period1: String,
        val period2: String,
        val interval: String,
        val includePrePost: String,
        val events: String,
)