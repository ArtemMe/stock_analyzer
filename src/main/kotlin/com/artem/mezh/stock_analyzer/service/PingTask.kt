package com.artem.mezh.stock_analyzer.service

import com.artem.mezh.stock_analyzer.client.TelegramApi
import com.artem.mezh.stock_analyzer.client.YahooFinanceApi
import com.artem.mezh.stock_analyzer.repository.entity.ShareEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.lang.StringBuilder
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit


@Service
class PingTask(
        val userService: UserService,
        val telegramApi: TelegramApi
) {
//    @Scheduled(fixedRateString = "\${ping-task.period}")
    fun pingMe() {
        val users = userService.findAll()

        if (users.isNotEmpty()) {
            val resultText = formatToString(getExpairedDividendDate(users[0].shares))
            if(resultText.isNotBlank()) {
                telegramApi.sendMessage(
                        mapOf(
                                Pair("chat_id", users[0].chatId.toString()),
                                Pair("text", resultText))
                )
            }
        }
    }

    private fun getExpairedDividendDate(shares: Set<ShareEntity>) : Map<String, LocalDateTime> {
        val sevenDayBefore = LocalDateTime.now().minusDays(SHIFT_DAY)
        return shares
                .filter { it.exDividend.isAfter(sevenDayBefore) }
                .map { it.symbol to it.exDividend }
                .toMap()
    }

    private fun formatToString(sharesMap: Map<String, LocalDateTime>?) : String {
        if(sharesMap == null || sharesMap.isEmpty()) return ""
        val result = sharesMap.entries
                .map { it.key + " " + it.value }
                .reduce {s1, s2 -> s1+"\n"}

        return addGreeting(result)
    }

    private fun addGreeting(str: String) : String {
        return "Привет! Не забудь, что менее чем через $SHIFT_DAY дней, по данным акциям дивиденты:\n$str"
    }

    companion object {
        const val SHIFT_DAY = 7L
    }
}