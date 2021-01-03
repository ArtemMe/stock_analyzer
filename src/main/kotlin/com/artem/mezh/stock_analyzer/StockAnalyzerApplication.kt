package com.artem.mezh.stock_analyzer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
class StockAnalyzerApplication

fun main(args: Array<String>) {
	runApplication<StockAnalyzerApplication>(*args)
}
