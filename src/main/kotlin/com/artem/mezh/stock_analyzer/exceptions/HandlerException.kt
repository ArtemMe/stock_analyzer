package com.artem.mezh.stock_analyzer.exceptions

class HandlerException(
        val typeEx: ExceptionType,
        val chatId: Long,
        message: String?
) : LogicException(message) {

    override fun getType(): ExceptionType {
        return typeEx
    }

    override fun chatId(): Long {
        return chatId
    }
}