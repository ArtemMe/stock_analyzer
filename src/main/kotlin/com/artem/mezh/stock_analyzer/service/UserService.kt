package com.artem.mezh.stock_analyzer.service

import com.artem.mezh.stock_analyzer.repository.entity.UserEntity
import com.artem.mezh.stock_analyzer.repository.entity.UserState
import com.artem.mezh.stock_analyzer.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService (
        val userRepository: UserRepository
) {
    fun findUserById(id: Int) : Optional<UserEntity> {
        return userRepository.findById(id.toString())
    }

    fun save(user: UserEntity): UserEntity {
        return userRepository.save(user)
    }

    fun updateState(user: UserEntity, userState: UserState?): UserEntity {
        val actualUser = findUserById(user.id.toInt()).get()
        actualUser.currentState = userState!!.name
        return userRepository.save(actualUser)
    }

    fun createUserIfEmpty(userId: Int): UserEntity {
        val userOpt = findUserById(userId)
        if (userOpt.isEmpty) {
            val newUser = buildUserEntity(userId.toString())
            return save(newUser)
        }

        return userOpt.get()
    }

    private fun buildUserEntity(id: String): UserEntity {
        return UserEntity(
                id = id,
                tickets = setOf(),
                currentState = UserState.MAIN_MENU.name
        )
    }
}