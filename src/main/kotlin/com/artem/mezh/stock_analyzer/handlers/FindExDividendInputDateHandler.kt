package com.artem.mezh.stock_analyzer.handlers

import com.artem.mezh.stock_analyzer.Command
import com.artem.mezh.stock_analyzer.exceptions.ExceptionType
import com.artem.mezh.stock_analyzer.exceptions.HandlerException
import com.artem.mezh.stock_analyzer.repository.entity.UserState
import com.artem.mezh.stock_analyzer.service.DividendsService
import com.artem.mezh.stock_analyzer.service.UserService
import com.artem.mezh.stock_analyzer.service.menu.CancelMenuService
import com.artem.mezh.stock_analyzer.service.menu.MainMenuService
import com.artem.mezh.stock_analyzer.supplier.dto.ExDividendDate
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class FindExDividendInputDateHandler(
        private val menuService: MainMenuService,
        private val dividendsService: DividendsService,
        private val userService: UserService
) : MainMenuHandlerI {

    override fun handle(context: CommandContext): SendMessage {

        //todo move to base class
        if(checkOnCancelCommand(context)) {
            userService.updateState(context.user, UserState.MAIN_MENU)
            return menuService.getMenu(context.message.chatId, "Вы вернулись в главное меню")
        }

        userService.updateState(context.user, UserState.MAIN_MENU)
        val ticket = checkMessageText(context.message.text)
        val exDividendDate = getDividend(ticket)

        return menuService.getMenu(context.message.chatId, exDividendDate)
    }

    override fun getStateType(): UserState {
        return UserState.FIND_EX_DIVIDEND_INPUT_TICKET
    }

    private fun checkMessageText(text: String?) : String {
        //TODO сделать обработку входных парметров
        return text!!
    }

    private fun getDividend(ticket: String) : String {
        val exDividend = dividendsService.getNextDividendDate(ticket)
                ?: return "Не найдено"

        return exDividend.fmt
    }

    private fun checkOnCancelCommand(commandContext: CommandContext) : Boolean {
        val text = commandContext.message.text
        return text != null && text == Command.CANCEL_COMMAND.menuName
    }
}