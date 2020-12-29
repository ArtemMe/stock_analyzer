package com.artem.mezh.stock_analyzer.exceptions

enum class ExceptionType(
        val desc: String
) {
        EMPTY_INPUT("Пустое сообщение!"),
        NOT_FOUND_EXERCISES("Пустое сообщение!"),
        NOT_FOUND_TREATMENT("Пустое сообщение!"),
        EMPTY_CALLBACK("Пустое сообщение!"),
}
