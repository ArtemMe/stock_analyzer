package com.artem.mezh.stock_analyzer

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [MongoInitializer::class],
        classes = [
            StockAnalyzerApplication::class
        ])
class BaseIntegrationTests {

    @Autowired(required = false)
    lateinit var wireMockServer: WireMockServer

    @BeforeEach
    protected fun cleanupCommon() {
        wireMockServer.resetAll()
    }

    fun yahooFinanceQuoteSummary(wireMockServer: WireMockServer) =
            wireMockServer.stubFor(get(urlPathMatching("/v10/finance/quoteSummary/.*"))
                    .willReturn(aResponse()
                            .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                            .withBody(StringLoader.fromClasspath("/__files/quote_summary_response.json"))
                            .withStatus(OK.value())))

    fun yahooFinanceQuoteSummaryEmptyDividend(wireMockServer: WireMockServer) =
            wireMockServer.stubFor(get(urlPathMatching("/v10/finance/quoteSummary/.*"))
                    .willReturn(aResponse()
                            .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                            .withBody(StringLoader.fromClasspath("/__files/quote_summary_empty_response.json"))
                            .withStatus(OK.value())))

    fun yahooFinanceChart(wireMockServer: WireMockServer) =
            wireMockServer.stubFor(get(urlPathMatching("/v8/finance/chart/.*"))
                    .willReturn(aResponse()
                            .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                            .withBody(StringLoader.fromClasspath("/__files/dividend_history.json"))
                            .withStatus(OK.value())))
}