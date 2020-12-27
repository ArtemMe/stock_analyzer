package com.artem.mezh.stock_analyzer

import com.artem.mezh.stock_analyzer.config.YahooFinanceConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableConfigurationProperties(YahooFinanceConfig::class)
@EnableFeignClients
class StockAnalyzerApplication

fun main(args: Array<String>) {
	runApplication<StockAnalyzerApplication>(*args)
}
