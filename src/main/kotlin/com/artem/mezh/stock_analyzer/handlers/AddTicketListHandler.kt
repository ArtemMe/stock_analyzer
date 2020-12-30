package com.artem.mezh.stock_analyzer.handlers

import com.artem.mezh.stock_analyzer.repository.entity.UserState
import com.artem.mezh.stock_analyzer.service.UserService
import com.artem.mezh.stock_analyzer.service.menu.CancelMenuService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class AddTicketListHandler(
        private val menuService: CancelMenuService,
        private val userService: UserService
) : MainMenuHandlerI {

    override fun handle(context: CommandContext): SendMessage {
        userService.updateState(context.user, UserState.ADD_TICKET_LIST_INPUT)
        return menuService.getMenu(context.message.chatId, "Введите список тикетов в столбик")
    }

    override fun getStateType(): UserState {
        return UserState.ADD_TICKET_LIST
    }

}