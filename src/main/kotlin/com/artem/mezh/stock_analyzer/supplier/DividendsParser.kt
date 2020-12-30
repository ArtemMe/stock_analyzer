package com.artem.mezh.stock_analyzer.supplier

import com.artem.mezh.stock_analyzer.supplier.dto.ShareDto
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class DividendsParser {

    //change parser to com.jayway.jsonpath
    fun getDividends(json: String?) : List<ShareDto> {
        val objectMapper = ObjectMapper()

        val rootNode: JsonNode = objectMapper.readTree(json)

        return rootNode
                .path("chart")
                .path("result")[0]
                .path("events")
                .path("dividends")
                .map { jsonNode ->  convertToShareDto(jsonNode) }
    }

    fun convertToShareDto(jsonNode: JsonNode) : ShareDto {
        return ShareDto(
                jsonNode.get("amount").asDouble(),
                jsonNode.get("date").asLong()
        )
    }
}