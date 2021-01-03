package com.artem.mezh.stock_analyzer.client

import com.artem.mezh.stock_analyzer.config.YahooFinanceConfig
import feign.QueryMap
import feign.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.*


@FeignClient(
        name = "telegram-api",
        url = "\${telegram.url}")
interface TelegramApi {
    @RequestMapping(path = ["/sendMessage"], method = [RequestMethod.GET])
    fun sendMessage(@SpringQueryMap queryParameters: Map<String, String>): Response?
}