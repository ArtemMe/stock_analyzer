package com.artem.mezh.stock_analyzer.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("User_stock")
data class UserEntity(
        @Id
        var id: String,
        var tickets: Set<String> = setOf(),
        var shares: Set<ShareEntity> = setOf(),
        var currentState: String,
        var chatId: Long
)
