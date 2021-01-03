package com.artem.mezh.stock_analyzer.service

import com.artem.mezh.stock_analyzer.supplier.YahooFinanceSupplier
import com.artem.mezh.stock_analyzer.supplier.dto.ExDividendDate
import com.artem.mezh.stock_analyzer.supplier.dto.ShareDto
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class DividendsService(
        private val yahooFinanceSupplier: YahooFinanceSupplier,
) {

    fun getDividends(ticketName: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime) : List<ShareDto> {
        return orderByDate(yahooFinanceSupplier.getDividendByTicket(ticketName, startDateTime, endDateTime))
    }

    fun getNextDividendDate(ticketName: String) : ExDividendDate? {
        return yahooFinanceSupplier.getCompanySummaryByTicket(ticketName)
                ?.exDividendDate
    }

    fun orderByDate(listShareDto: List<ShareDto>) : List<ShareDto> {
        return listShareDto.sortedBy { shareDto -> shareDto.date }
    }

    fun getListNextDividendsDate(ticketList: List<String>): Map<String, LocalDateTime?> {
        return ticketList
                .map { it to convertToDate(getNextDividendDate(it)?.raw) }
                .toMap()
    }

    fun convertToDate(unixDateTime: Long?) : LocalDateTime? {
        if(unixDateTime == null) return null
        return LocalDateTime.ofEpochSecond(unixDateTime,0, ZoneOffset.UTC)
    }
}