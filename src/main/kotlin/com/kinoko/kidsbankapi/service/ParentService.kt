package com.kinoko.kidsbankapi.service

import com.kinoko.kidsbankapi.dto.User
import com.kinoko.kidsbankapi.dto.request.RegistrationRequest
import com.kinoko.kidsbankapi.entity.AccountEntity
import com.kinoko.kidsbankapi.entity.UserEntity
import com.kinoko.kidsbankapi.enums.Role
import com.kinoko.kidsbankapi.exception.AuthenticationException
import com.kinoko.kidsbankapi.exception.UserNotFoundException
import com.kinoko.kidsbankapi.repository.AccountRepository
import com.kinoko.kidsbankapi.repository.UserRepository
import com.kinoko.kidsbankapi.util.toUserDTO
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

        if (userRepository.findByLogin(request.username) != null) {
            logger.warn("Registration error: User with ${request.username} is success")
            throw AuthenticationException("Пользователь с таким логином уже существует")
        }

        val child = UserEntity (
            login = request.username,
            authPassword = passwordEncoder.encode(request.password),
            lastname = request.lastname.trim(),
            name = request.name.trim(),
            fatherName = request.fatherName.trim(),
            birthDate = request.birthDate,
            city = request.city.trim(),
            role = Role.CHILD,
            child = null
        )
        userRepository.save(child)

        val parent = userRepository.findByLogin(jwtService.getLogin(token))
            ?: throw UserNotFoundException("Пользователь не найден")

        parent.child = child
        userRepository.save(parent)

        val userAccount = AccountEntity(user = child, balance = 0)
        accountRepository.save(userAccount)
        logger.debug("Ребенок с username ${child.login} был создан")
        logger.info("Регистрация ребенка прошла успешно!")

        return child.toUserDTO()
    }

    fun getSalary(token: String) {
        val user = userRepository.findByLogin(jwtService.getLogin(token))
            ?: throw UserNotFoundException("Пользователь не найден")
        val account = accountRepository.findAccountEntityByUser(user)

        account.balance += 20000
        accountRepository.save(account)
        logger.info("Родитель получил зарплату 20 000 рублей")
    }

    private fun isValidCredentials(request: RegistrationRequest) =
        request.username.isNotEmpty() && request.password.isNotEmpty()

}
