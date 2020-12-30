package com.artem.mezh.stock_analyzer.supplier

import com.artem.mezh.stock_analyzer.client.YahooFinanceApi
import com.artem.mezh.stock_analyzer.supplier.dto.CompanySummary
import com.artem.mezh.stock_analyzer.supplier.dto.ShareDto
import feign.Response
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset


@Component
class YahooFinanceSupplier(
        private val dividendsParser: DividendsParser,
        private val quoteSummaryParser: QuoteSummaryParser,
        private val yahooFinanceApi: YahooFinanceApi
) {
    fun getDividendByTicket(ticketName: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<ShareDto> {
        val result = yahooFinanceApi.charInfo(
                ticketName,
                buildReqParam(ticketName, startDateTime, endDateTime))

        return dividendsParser.getDividends(getAsString(result?.body()))
    }

    fun getCompanySummaryByTicket(ticketName: String): CompanySummary? {
        val result = yahooFinanceApi.companyInfo(ticketName, "calendarEvents")
        return quoteSummaryParser.getSummary(getAsString(result?.body()))
    }

    fun getAsString(body: Response.Body?) : String? {
        if(body == null) return null
        return String(body.asInputStream()?.readAllBytes()!!)
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

    private fun getTimestamp(date: LocalDateTime): String {
        return date.atZone(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(0))).toEpochSecond().toString()
    }
}