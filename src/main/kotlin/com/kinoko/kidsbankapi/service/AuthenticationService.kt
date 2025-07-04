package com.kinoko.kidsbankapi.service

import com.kinoko.kidsbankapi.dto.User
import com.kinoko.kidsbankapi.dto.request.AuthenticationRequest
import com.kinoko.kidsbankapi.dto.request.RegistrationRequest
import com.kinoko.kidsbankapi.dto.response.AuthenticationResponse
import com.kinoko.kidsbankapi.entity.AccountEntity
import com.kinoko.kidsbankapi.entity.UserEntity
import com.kinoko.kidsbankapi.enum.Role
import com.kinoko.kidsbankapi.repository.AccountRepository
import com.kinoko.kidsbankapi.repository.UserRepository
import com.kinoko.kidsbankapi.util.convertToUserDTO
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val accountRepository: AccountRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(AuthenticationService::class.java)

    @Transactional
    fun authorization(request: AuthenticationRequest, response: HttpServletResponse): AuthenticationResponse {
        if (!isValidCredentials(request)) {
            logger.error("Login and/or password is empty")
            throw Exception("Поля логин и/или пароль пустые")
        }
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.login, request.password))
        val user = userRepository.findByLogin(request.login)
        logger.debug("User ${user.login} is authorized")
        logger.info("Authorization is successful!")

        val tokens = jwtService.generateTokens(user)

        setRefreshToken(response, tokens[1])

        return AuthenticationResponse(tokens[0], user.convertToUserDTO())
    }

    @Transactional
    fun registration(request: RegistrationRequest, response: HttpServletResponse): AuthenticationResponse {
        if (!isValidCredentials(request)) {
            logger.error("Заполнены не все данные")
            throw Exception("Заполнены не все данные")
        }

        val usernames = userRepository.findAllUsernames()

        usernames.forEach { username ->
            if (request.username == username) {
                logger.error("Ошибка регистрации: Пользователь с username ${request.username} уже существует")
                throw Exception("Пользователь с таким username уже существует")
            }
        }

        val user = UserEntity (
            login = request.username,
            authPassword = passwordEncoder.encode(request.password),
            lastname = request.lastname,
            name = request.name,
            fatherName = request.fatherName,
            birthDate = request.birthDate,
            city = request.city,
            role = Role.PARENT,
            child = null
        )
        val tokens = jwtService.generateTokens(user)

        userRepository.save(user)

        val userAccount = AccountEntity(user = user, balance = 0)
        accountRepository.save(userAccount)

        setRefreshToken(response, tokens[1])

        logger.debug("Пользователь с username ${user.login} был создан")
        logger.info("Регистрация прошла успешно")

        return AuthenticationResponse(tokens[0], user.convertToUserDTO())
    }

    fun logout(token: String, response: HttpServletResponse): Map<String, String> {
        val cookie = Cookie("refreshToken", null)
        cookie.maxAge = 0
        cookie.path = "/"
        response.addCookie(cookie)

        return mapOf("message" to "Logout successful")
    }

    @Transactional
    fun refresh(userToken: String, response: HttpServletResponse): AuthenticationResponse {
        if (userToken.isEmpty()) {
            logger.error("Токен пуст")
            throw Exception("Токен пуст")
        }

        val user = userRepository.findByLogin(jwtService.extractUsername(userToken))
        val tokens = jwtService.generateTokens(user)

        setRefreshToken(response, tokens[1])

        logger.debug("Токен пользователя ${user.login} обновлен")

        return AuthenticationResponse(tokens[0], user.convertToUserDTO())
    }

    fun whoAmI(token: String): User {
        val userEntity = userRepository.findByLogin(jwtService.extractUsername(token.substring(7)))
        val user = userEntity.convertToUserDTO()
        logger.info("WhoAmI для пользователя ${user.username} был возвращен")
        return user
    }

    private fun setRefreshToken(response: HttpServletResponse, token: String) {
        val cookie = ResponseCookie.from("refreshToken", "Bearer_$token")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofDays(30))
            .sameSite("None")
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
    }

    private fun isValidCredentials(request: AuthenticationRequest) =
        request.login.isNotEmpty() && request.password.isNotEmpty()

    private fun isValidCredentials(request: RegistrationRequest) =
        request.username.isNotEmpty() && request.password.isNotEmpty()

}
