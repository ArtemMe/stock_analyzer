package com.artem.mezh.stock_analyzer.handlers

import com.artem.mezh.stock_analyzer.exceptions.ExceptionType
import com.artem.mezh.stock_analyzer.exceptions.HandlerException
import com.artem.mezh.stock_analyzer.repository.entity.UserState
import com.artem.mezh.stock_analyzer.service.UserService
import com.artem.mezh.stock_analyzer.service.menu.MainMenuService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import java.util.stream.IntStream

@Service
class AddTicketListInputHandler(
        private val menuService: MainMenuService,
        private val userService: UserService
) : MainMenuHandlerI {

    override fun handle(context: CommandContext): SendMessage {
        checkCancelCommand(context.message.text, context.message.chatId)

        userService.updateState(context.user, UserState.MAIN_MENU)
        val tickets = checkMessageText(context.message.text)
        addTicketsToUser(context.user.id, tickets)

        return menuService.getMenu(context.message.chatId, createResponse(tickets))
    }

    override fun getStateType(): UserState {
        return UserState.ADD_TICKET_LIST_INPUT
    }

    private fun createResponse(tickets: Set<String>) : String {
        return "Добавил тебе такой список тикетов:\n" + stringToColumn(tickets)
    }

    private fun stringToColumn(input: Set<String>) : String {
        return input.reduce { s1, s2 -> s1 + "\n" + s2 }
    }

    private fun checkMessageText(text: String?) : Set<String> {
        //TODO сделать обработку входных парметров

        if(text.isNullOrBlank()) return setOf()
        return text.split("\n")
                .filter { it.isNotBlank() }
               //.filter { checkLatin(it.chars()) }
                .map { it.trim() }
                .toSet()
    }

    private fun checkLatin(chars: IntStream) : Boolean {
        return chars.allMatch { Character.UnicodeBlock.BASIC_LATIN.equals(it) }
    }

    private fun addTicketsToUser(userId: String, newTickets: Set<String>) {
        val user = userService.findUserById(userId.toInt()).get()
        user.tickets.union(newTickets)
        userService.save(user)
    }

    private fun checkCancelCommand(text: String?, chatId: Long) {
        if(text == UserState.CANCEL.name) throw HandlerException(ExceptionType.EMPTY_INPUT, chatId, "Вернемся в зад")
    }
}