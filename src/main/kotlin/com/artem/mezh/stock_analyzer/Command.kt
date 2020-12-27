package com.artem.mezh.stock_analyzer

enum class Command(val desc: String, val menuName: String) {
    FIND_EX_DIVIDEND("/find_ex_div_date", "Дата дивидента")
}