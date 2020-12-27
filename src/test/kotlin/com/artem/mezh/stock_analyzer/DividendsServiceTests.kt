package com.artem.mezh.stock_analyzer

import com.artem.mezh.stock_analyzer.service.DividendsService
import com.artem.mezh.stock_analyzer.supplier.dto.ShareDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import java.time.ZoneOffset

class DividendsServiceTests : BaseIntegrationTests() {

    @Autowired
    lateinit var dividendsService: DividendsService

	@Test
	fun getDividendHistory() {
        yahooFinanceChart(wireMockServer)
        val result = dividendsService.getDividends(
                "MSFT",
                LocalDateTime.of(2019, 8,14,13,30),
                LocalDateTime.of(2019, 11,20,14,30)
        )

        assert(VALID_HISTORY == result)
	}
//    LocalDateTime.of(2019, 8,14,13,30),
//    LocalDateTime.of(2019, 11,20,14,30),
//    LocalDateTime.of(2020, 5,20,13,30),
//    LocalDateTime.of(2020, 2,19,14,30),

    @Test
    fun getNextDividend() {
        yahooFinanceQuoteSummary(wireMockServer)
        var result = dividendsService.getNextDividendDate("MSFT")

        assert(result.fmt == "2021-03-11")
    }

    @Test
    fun getListNextDividends() {
        yahooFinanceQuoteSummary(wireMockServer)
        var result = dividendsService.getListNextDividendsDate(listOf("MSFT", "CSCO"))

        assert(result.get("MSFT") == LocalDateTime.of(2021, 3,11,0,0))
        assert(result.get("CSCO") == LocalDateTime.of(2021, 3,11,0,0))
    }

    fun printWithWhitespace(shareDto: ShareDto) {
        print(shareDto.dividentPrice)
        print(" ")
        print(LocalDateTime.ofEpochSecond(shareDto.date, 0, ZoneOffset.UTC))
        print("\n")
    }

    companion object {
        val VALID_HISTORY = listOf(
                ShareDto(0.46, 1565789400),
                ShareDto(0.51, 1574260200),
                ShareDto(0.51, 1582122600),
                ShareDto(0.51, 1589981400),
        )
    }
}
