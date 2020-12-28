package com.artem.mezh.stock_analyzer.handlers

import com.artem.mezh.stock_analyzer.repository.entity.UserState
import org.springframework.stereotype.Service

@Service
class MessageHandlerFactory(
        private val messageHandlers: List<MessageHandler>
) {
    fun getHandler(state: UserState): MessageHandler {
        return messageHandlers
                .first { handler -> handler.getStateType() == state }
    }
}