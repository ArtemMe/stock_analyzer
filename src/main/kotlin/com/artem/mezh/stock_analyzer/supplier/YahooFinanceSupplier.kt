package com.artem.mezh.stock_analyzer.supplier

import com.artem.mezh.stock_analyzer.client.ChartInfoParams
import com.artem.mezh.stock_analyzer.client.YahooFinanceApi
import com.artem.mezh.stock_analyzer.config.YahooFinanceConfig
import com.artem.mezh.stock_analyzer.supplier.dto.CompanySummary
import com.artem.mezh.stock_analyzer.supplier.dto.ShareDto
import feign.Body
import feign.Response
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset


@Component
class YahooFinanceSupplier(
        private val dividendsParser: DividendsParser,
        private val quoteSummaryParser: QuoteSummaryParser,
        private val yahooFinanceConfig: YahooFinanceConfig,
        private val yahooFinanceApi: YahooFinanceApi
) {
    fun getDividendByTicket2(ticketName: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<ShareDto> {
        val result = yahooFinanceApi.charInfo(
                ticketName,
                buildReqParam(ticketName, startDateTime, endDateTime))

        return dividendsParser.getDividends(getAsString(result?.body()!!))
    }

    fun getCompanySummaryByTicket2(ticketName: String): CompanySummary {
        val result = yahooFinanceApi.companyInfo(ticketName, "calendarEvents")
        return quoteSummaryParser.getSummary(getAsString(result?.body()!!))
    }

    fun getAsString(body: Response.Body) : String {
        return String(body.asInputStream()?.readAllBytes()!!)
    }

    private fun buildUrl(url: String, ticketName: String): String {
        return String.format(url, ticketName)
    }

    private fun buildReqParam(ticketName: String,
                              startDateTime: LocalDateTime,
                              endDateTime: LocalDateTime)
            : Map<String, String> {

        return mutableMapOf(
                Pair("symbol", ticketName),
                Pair("period1", getTimestamp(startDateTime)),
                Pair("period2", getTimestamp(endDateTime)),
                Pair("interval", "1mo"),
                Pair("includePrePost", "true"),
                Pair("events", "div%7Csplit"),
        )
    }

    private fun buildCalendarEventsReqParam(): Map<String, String> {
        return mutableMapOf(
                Pair("modules", "calendarEvents"),
        )
    }

    private fun getTimestamp(date: LocalDateTime): String {
        return date.atZone(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(0))).toEpochSecond().toString()
    }

    fun getDividendByTicket(ticketName: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<ShareDto> {
        val restTemplate = RestTemplate()
        val response: ResponseEntity<String> = restTemplate.getForEntity(
                buildUrl(yahooFinanceConfig.chartUrl!!, ticketName),
                String::class.java,
                buildReqParam(ticketName, startDateTime, endDateTime))

        return dividendsParser.getDividends(response.body)
    }

    fun getCompanySummaryByTicket(ticketName: String): CompanySummary {
        val restTemplate = RestTemplate()
        val response: ResponseEntity<String> = restTemplate.getForEntity(
                buildUrl(yahooFinanceConfig.quoteSummaryUrl!!, ticketName),
                String::class.java,
                buildCalendarEventsReqParam())

        return quoteSummaryParser.getSummary(response.body)
    }

}