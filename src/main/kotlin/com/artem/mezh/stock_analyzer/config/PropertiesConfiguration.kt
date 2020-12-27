package com.artem.mezh.stock_analyzer.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(
        BotConfig::class,
        TaskConfig::class
)
@Configuration
class PropertiesConfiguration