package com.artem.mezh.stock_analyzer.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("User_stock")
data class UserEntity(
        @Id
        var id: String,
        var tickets: List<String>? = null,
        var currentState: String
)
