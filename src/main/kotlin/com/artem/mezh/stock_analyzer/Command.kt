package com.artem.mezh.stock_analyzer

enum class Command(val desc: String, val menuName: String) {
    FIND_EX_DIVIDEND("/find_ex_div_date", "Дата дивидента"),
    ADD_TICKET_LIST("/add_ticket_list", "Добавить тикеты"),
    CANCEL_COMMAND("/cancel", "Отмена")
}