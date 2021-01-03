package com.artem.mezh.stock_analyzer.service

import org.springframework.stereotype.Service

@Service
class TicketService(
        val fileService: FileService
) {
    fun getBanchTickets(number: Integer, startTicket: String): List<String> {


        return emptyList()
    }
}