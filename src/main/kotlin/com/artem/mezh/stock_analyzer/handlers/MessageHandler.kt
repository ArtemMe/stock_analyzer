package com.artem.mezh.stock_analyzer.handlers

import com.artem.mezh.stock_analyzer.repository.entity.UserState
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

interface MessageHandler {
    fun handle(context: CommandContext) : SendMessage
    fun getStateType() : UserState
}