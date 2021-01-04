package com.artem.mezh.stock_analyzer


import com.artem.mezh.stock_analyzer.repository.UserRepository
import com.artem.mezh.stock_analyzer.repository.entity.ShareEntity
import com.artem.mezh.stock_analyzer.repository.entity.UserEntity
import com.artem.mezh.stock_analyzer.repository.entity.UserState
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

class WebHookControllerTest : BaseIntegrationTests() {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun cleanMongo() {
        userRepository.deleteAll()
    }

    @Test
    fun `should successful save to mongo user info`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/")
                .contentType("application/json")
                .content(StringLoader.fromClasspath("/__files/telegram_req.json")))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val user = userRepository.findById("100000000")

        assert(user.isPresent)
    }

    @Test
    fun `should successful process command FIND_EX_DIVIDEND`() {
        val result = mockMvc.perform(MockMvcRequestBuilders.post("/")
                .contentType("application/json")
                .content(StringLoader.fromClasspath("/__files/telegram_date_dividend_req.json")))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val user = userRepository.findById("100000000")

        assert(user.isPresent)
        assert(user.get().currentState == UserState.FIND_EX_DIVIDEND_INPUT_TICKET.name)


        result
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.text", Matchers.containsString("Введите название тикета акции")))

    }

    @Test
    fun `should successful process state FIND_EX_DIVIDEND_INPUT_TICKET`() {
        generateUserRecordWithState(UserState.FIND_EX_DIVIDEND_INPUT_TICKET)
        yahooFinanceQuoteSummary(wireMockServer)

        val result = mockMvc.perform(MockMvcRequestBuilders.post("/")
                .contentType("application/json")
                .content(StringLoader.fromClasspath("/__files/telegram_date_dividend_input_req.json")))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val user = userRepository.findById("100000000")

        assert(user.isPresent)
        assert(user.get().currentState == UserState.MAIN_MENU.name)

        result
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.text", Matchers.containsString("2021-03-11")))

    }

    @Test
    fun `should successful process state ADD_TICKET_LIST`() {
        generateUserRecordWithState(UserState.ADD_TICKET_LIST)
        yahooFinanceQuoteSummary(wireMockServer)

        val result = mockMvc.perform(MockMvcRequestBuilders.post("/")
                .contentType("application/json")
                .content(StringLoader.fromClasspath("/__files/telegram_add_ticket_input_req.json")))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val user = userRepository.findById("100000000")

        assert(user.isPresent)
        assert(user.get().currentState == UserState.ADD_TICKET_LIST_INPUT.name)

        result
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.text", Matchers.containsString("Введите список тикетов в столбик")))
    }

    @Test
    fun `should successful process state ADD_TICKET_LIST_INPUT`() {
        generateUserRecordWithState(UserState.ADD_TICKET_LIST_INPUT)
        yahooFinanceQuoteSummary(wireMockServer)

        val result = mockMvc.perform(MockMvcRequestBuilders.post("/")
                .contentType("application/json")
                .content(StringLoader.fromClasspath("/__files/telegram_add_ticket_input_req.json")))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val user = userRepository.findById("100000000")

        assert(user.isPresent)
        assert(user.get().currentState == UserState.MAIN_MENU.name)
        assert(user.get().shares == setOf(
                ShareEntity("MMM", LocalDateTime.of(2021, 3, 11, 0, 0)),
                ShareEntity("CSCO", LocalDateTime.of(2021, 3, 11, 0, 0)),
                ShareEntity("MSMF", LocalDateTime.of(2021, 3, 11, 0, 0)),
        ))

        result
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.text", Matchers.containsString("Добавил тебе такой список тикетов:\nMMM\nCSCO\nMSMF")))
    }

    private fun generateUserRecordWithState(userState: UserState) {
        userRepository.save(UserEntity(
                id = "100000000",
                chatId = 100000001,
                currentState = userState.name
        ))
    }
}