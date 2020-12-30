package com.artem.mezh.stock_analyzer.handlers

import com.artem.mezh.stock_analyzer.repository.entity.UserState
import com.artem.mezh.stock_analyzer.service.UserService
import com.artem.mezh.stock_analyzer.service.menu.MainMenuService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class CancelHandler(
        private val menuService: MainMenuService,
        private val userService: UserService
) : MainMenuHandlerI {

    override fun handle(context: CommandContext): SendMessage {
        userService.updateState(context.user, UserState.MAIN_MENU)
        return menuService.getMenu(context.message.chatId, "В главном меню теперь Вы")
    }

    override fun getStateType(): UserState {
        return UserState.CANCEL
    }

}