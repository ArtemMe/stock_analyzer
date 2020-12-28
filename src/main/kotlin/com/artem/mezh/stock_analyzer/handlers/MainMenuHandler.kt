package com.artem.mezh.stock_analyzer.handlers

import com.artem.mezh.stock_analyzer.Command
import com.artem.mezh.stock_analyzer.repository.entity.UserState
import com.artem.mezh.stock_analyzer.service.menu.MainMenuService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class MainMenuHandler(
        private val messageHandlers: List<MainMenuHandlerI>,
        private val menuService: MainMenuService
) : MessageHandler {

    override fun handle(context: CommandContext): SendMessage {
        val handlerName = findHandlerName(context.message.text)
                ?: return menuService.getMenu(context.message.chatId, "Главное меню")

        return getHandler(handlerName)
                .handle(context)
    }

    override fun getStateType(): UserState {
        return UserState.MAIN_MENU
    }

    private fun findHandlerName(messageText: String?) : UserState? {
        if (messageText == null) return null

        return when {
            checkCommand(Command.FIND_EX_DIVIDEND, messageText) -> UserState.FIND_EX_DIVIDEND
            else -> null
        }
    }

    fun checkCommand(command: Command, msg: String?): Boolean {
        if (msg == null) return false
        return msg.contains(command.desc, true) || msg.contains(command.menuName, true)
    }

    fun getHandler(state: UserState): MessageHandler {
        return messageHandlers
                .first { handler -> handler.getStateType() == state }
    }
}