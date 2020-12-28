package com.artem.mezh.stock_analyzer.handlers

import com.artem.mezh.stock_analyzer.repository.entity.UserEntity
import org.telegram.telegrambots.meta.api.objects.Message

data class CommandContext(
        val message: Message,
        val user: UserEntity
)