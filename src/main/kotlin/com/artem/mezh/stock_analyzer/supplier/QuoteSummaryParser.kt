package com.artem.mezh.stock_analyzer.supplier

import com.artem.mezh.stock_analyzer.supplier.dto.CompanySummary
import com.artem.mezh.stock_analyzer.supplier.dto.ExDividendDate
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class QuoteSummaryParser {
    fun getSummary(body: String?): CompanySummary {
        val objectMapper = ObjectMapper()

        val rootNode: JsonNode = objectMapper.readTree(body)

        val dividendDate = rootNode
                .path("quoteSummary")
                .path("result")[0]
                .path("calendarEvents")
                .path("dividendDate")

        return convertToShareDto(dividendDate)
    }

    fun convertToShareDto(jsonNode: JsonNode) : CompanySummary {
        return CompanySummary(
                ExDividendDate(
                        jsonNode.get("raw").asLong(),
                        jsonNode.get("fmt").asText()
                )
        )
    }
}