package com.artem.mezh.stock_analyzer.service.menu

import com.artem.mezh.stock_analyzer.Command
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import java.util.ArrayList

@Service
class CancelMenuService(
        val menuService: MenuService
) {
    fun getMenu(chatId: Long, textMessage: String): SendMessage {
        val replyKeyboardMarkup = mainMenuKeyboard
        return menuService.createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup)
    }

    private val mainMenuKeyboard: ReplyKeyboardMarkup
        private get() {
            val replyKeyboardMarkup = ReplyKeyboardMarkup()
            replyKeyboardMarkup.selective = true
            replyKeyboardMarkup.resizeKeyboard = true
            replyKeyboardMarkup.oneTimeKeyboard = false
            val keyboard: MutableList<KeyboardRow> = ArrayList()
            val row1 = KeyboardRow()
            row1.add(KeyboardButton(Command.CANCEL_COMMAND.menuName))
            keyboard.add(row1)
            replyKeyboardMarkup.keyboard = keyboard
            return replyKeyboardMarkup
        }
}