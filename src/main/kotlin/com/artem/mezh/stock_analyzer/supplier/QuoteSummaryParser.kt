package com.artem.mezh.stock_analyzer.supplier

import com.artem.mezh.stock_analyzer.supplier.dto.CompanySummary
import com.artem.mezh.stock_analyzer.supplier.dto.ExDividendDate
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import org.springframework.stereotype.Component

@Component
class QuoteSummaryParser {
    fun getSummary(body: String?): CompanySummary? {
        val objectMapper = ObjectMapper()

        val rootNode: JsonNode = objectMapper.readTree(body)

        if(checkIfError(rootNode)) return null

        val dividendDate = rootNode
                .path("quoteSummary")
                .path("result")[0]
                .path("calendarEvents")
                .path("dividendDate")

        return convertToCompanySummary(dividendDate)
    }

    fun convertToCompanySummary(jsonNode: JsonNode) : CompanySummary? {
        if(jsonNode.isEmpty) return null
        return CompanySummary(
                ExDividendDate(
                        jsonNode.get("raw").asLong(),
                        jsonNode.get("fmt").asText()
                )
        )
    }

    fun checkIfError(jsonNode: JsonNode) : Boolean {
        val errorDescription = jsonNode
                .path("quoteSummary")
                .path("error")
                .path("description")

        return errorDescription != null && !errorDescription.asText().isNullOrBlank()
    }

    fun getByPath(path: String, json: String) : String {
        val path = "$.quoteSummary.result[0].calendarEvents.dividendDate"
        val jsonContext: DocumentContext = JsonPath.parse(json)
        val dividend: String = jsonContext.read(path)
        return dividend
    }
}