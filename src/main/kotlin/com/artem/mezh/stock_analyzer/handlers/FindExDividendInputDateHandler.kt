package com.artem.mezh.stock_analyzer.handlers

import com.artem.mezh.stock_analyzer.repository.entity.UserState
import com.artem.mezh.stock_analyzer.service.DividendsService
import com.artem.mezh.stock_analyzer.service.UserService
import com.artem.mezh.stock_analyzer.service.menu.CancelMenuService
import com.artem.mezh.stock_analyzer.service.menu.MainMenuService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class FindExDividendInputDateHandler(
        private val menuService: MainMenuService,
        private val dividendsService: DividendsService,
        private val userService: UserService
) : MainMenuHandlerI {

    override fun handle(context: CommandContext): SendMessage {
        userService.updateState(context.user, UserState.MAIN_MENU)
        val ticket = checkMessageText(context.message.text)
        val exDividendDate = dividendsService.getNextDividendDate(ticket).fmt

        return menuService.getMenu(context.message.chatId, exDividendDate)
    }

    override fun getStateType(): UserState {
        return UserState.FIND_EX_DIVIDEND_INPUT_TICKET
    }

    private fun checkMessageText(text: String?) : String {
        //TODO сделать обработку входных парметров
        return text!!
    }
}