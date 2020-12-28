package com.artem.mezh.stock_analyzer.handlers

import com.artem.mezh.stock_analyzer.repository.entity.UserState
import com.artem.mezh.stock_analyzer.service.UserService
import com.artem.mezh.stock_analyzer.service.menu.CancelMenuService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class FindExDividendHandler(
        private val menuService: CancelMenuService,
        private val userService: UserService
) : MainMenuHandlerI {

    override fun handle(context: CommandContext): SendMessage {
        userService.updateState(context.user, UserState.FIND_EX_DIVIDEND_INPUT_TICKET)
        return menuService.getMenu(context.message.chatId, "Введите название тикета акции")
    }

    override fun getStateType(): UserState {
        return UserState.FIND_EX_DIVIDEND
    }

}