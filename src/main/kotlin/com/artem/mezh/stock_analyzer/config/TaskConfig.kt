package com.artem.mezh.stock_analyzer.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "ping-task")
data class TaskConfig (
        var url: String?
)