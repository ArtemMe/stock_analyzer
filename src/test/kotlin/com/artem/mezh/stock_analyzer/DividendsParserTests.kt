package com.artem.mezh.stock_analyzer

import com.artem.mezh.stock_analyzer.service.DividendsService
import com.artem.mezh.stock_analyzer.supplier.DividendsParser
import com.artem.mezh.stock_analyzer.supplier.dto.ShareDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class DividendsParserTests : BaseIntegrationTests() {
    @Autowired
    lateinit var dividendsParser: DividendsParser

    @Test
    fun contextLoads() {
        var result = dividendsParser.getDividends(StringLoader.fromClasspath("/__files/dividend_history.json"))

        assert(result == VALID_HISTORY)
    }

    companion object {
        val VALID_HISTORY = listOf(
                ShareDto(0.46, 1565789400),
                ShareDto(0.51, 1574260200),
                ShareDto(0.51, 1589981400),
                ShareDto(0.51, 1582122600),
        )
    }
}