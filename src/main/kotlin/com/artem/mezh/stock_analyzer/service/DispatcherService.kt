package com.artem.mezh.stock_analyzer.serviceimport com.artem.mezh.stock_analyzer.exceptions.ExceptionTypeimport com.artem.mezh.stock_analyzer.exceptions.HandlerExceptionimport com.artem.mezh.stock_analyzer.handlers.CommandContextimport com.artem.mezh.stock_analyzer.handlers.MessageHandlerFactoryimport com.artem.mezh.stock_analyzer.repository.entity.UserStateimport org.springframework.stereotype.Serviceimport org.telegram.telegrambots.meta.api.methods.send.SendMessageimport org.telegram.telegrambots.meta.api.objects.CallbackQueryimport org.telegram.telegrambots.meta.api.objects.Message@Serviceclass DispatcherService(        private val userService: UserService,        private val messageHandlerFactory: MessageHandlerFactory) {    fun handleCallback(callbackQuery: CallbackQuery?): SendMessage {        TODO("Not yet implemented")    }    fun handleMessage(message: Message?): SendMessage {        if (message != null) {            return internalHandleMessage(message)        }        return SendMessage()    }    fun internalHandleMessage(message: Message): SendMessage {        checkMessage(message)        val userId = message.from!!.id        val user = userService.createUserIfEmpty(userId, message.chatId)        val handler = messageHandlerFactory.getHandler(UserState.valueOf(user.currentState))        return handler.handle(CommandContext(message, user))    }    private fun checkMessage(msg: Message) {        msg.text ?: throw HandlerException(ExceptionType.EMPTY_INPUT, msg.chatId, "Пустое сообщение!")    }}