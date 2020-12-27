package com.artem.mezh.stock_analyzer

import com.artem.mezh.stock_analyzer.supplier.YahooFinanceSupplier
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime


class YahooFinanceSupplierTests : BaseIntegrationTests() {

    @Autowired
    lateinit var yahooFinanceSupplier: YahooFinanceSupplier

	@Test
	fun contextLoads() {
        yahooFinanceChart(wireMockServer)
        var result = yahooFinanceSupplier.getDividendByTicket2(
                "MSFT",
                LocalDateTime.of(2015, 1,1,0,0),
                LocalDateTime.of(2020, 1,1,0,0)
        )

        println(result)
	}

}
