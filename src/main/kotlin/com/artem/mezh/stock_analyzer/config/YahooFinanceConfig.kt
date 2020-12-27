package com.artem.mezh.stock_analyzer.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "yahoo-finance")
data class YahooFinanceConfig(
        var quoteSummaryUrl: String? = null,
        var chartUrl: String? = null,
)