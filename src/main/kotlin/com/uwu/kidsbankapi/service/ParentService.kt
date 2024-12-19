package com.uwu.kidsbankapi.service

import com.uwu.kidsbankapi.dto.User
import com.uwu.kidsbankapi.dto.request.RegistrationRequest
import com.uwu.kidsbankapi.entity.AccountEntity
import com.uwu.kidsbankapi.entity.UserEntity
import com.uwu.kidsbankapi.enum.Role
import com.uwu.kidsbankapi.repository.AccountRepository
import com.uwu.kidsbankapi.repository.UserRepository
import com.uwu.kidsbankapi.util.convertToUserDTO
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class ParentService(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: BCryptPasswordEncoder
) {
   private val logger = LoggerFactory.getLogger(ParentService::class.java)

    fun createChildAccount(request: RegistrationRequest, token: String): User {
        if (!isValidCredentials(request)) {
            logger.error("Заполнены не все данные")
            throw Exception("Заполнены не все данные")
        }

        val usernames = userRepository.findAllUsernames()

        usernames.forEach { username ->
            if (request.username == username) {
                logger.error("Ошибка при регистрации: Пользователь с username ${request.username} уже существует")
                throw Exception("Пользователь с таким username уже существует")
            }
        }

        val child = UserEntity (
            login = request.username,
            authPassword = passwordEncoder.encode(request.password),
            lastname = request.lastname,
            name = request.name,
            fatherName = request.fatherName,
            birthDate = request.birthDate,
            city = request.city,
            role = Role.CHILD,
            child = null
        )
        userRepository.save(child)

        val parent = userRepository.findByLogin(jwtService.extractUsername(token))
        parent.child = child
        userRepository.save(parent)

        val userAccount = AccountEntity(user = child, balance = 0)
        accountRepository.save(userAccount)
        logger.debug("Ребенок с username ${child.login} был создан")
        logger.info("Регистрация ребенка прошла успешно!")

        return child.convertToUserDTO()
    }

    fun getSalary(token: String) {
        val account = accountRepository.findAccountEntityByUser(
            userRepository.findByLogin(jwtService.extractUsername(token))
        )

        account.balance += 20000
        accountRepository.save(account)
        logger.info("Родитель получил зарплату 20 000 рублей")
    }

    private fun isValidCredentials(request: RegistrationRequest) =
        request.username.isNotEmpty() && request.password.isNotEmpty()

}