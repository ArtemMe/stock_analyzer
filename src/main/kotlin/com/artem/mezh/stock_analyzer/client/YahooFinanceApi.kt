package com.artem.mezh.stock_analyzer.client

import com.artem.mezh.stock_analyzer.config.YahooFinanceConfig
import feign.QueryMap
import feign.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.*


@FeignClient(
        name = "yahoo-finance",
        url = "\${yahoo-finance.url}",
        configuration = [YahooFinanceConfig::class])
interface YahooFinanceApi {
    @RequestMapping(path = ["/v10/finance/quoteSummary/{ticket}"], method = [RequestMethod.GET])
    fun companyInfo(@PathVariable ticket: String, @RequestParam modules: String): Response?

    @RequestMapping(path = ["/v8/finance/chart/{ticket}"], method = [RequestMethod.GET])
    fun charInfo(@PathVariable ticket: String, @SpringQueryMap queryParameters: Map<String, String>): Response?
}