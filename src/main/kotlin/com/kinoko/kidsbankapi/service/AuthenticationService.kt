package com.kinoko.kidsbankapi.service

import com.kinoko.kidsbankapi.dto.User
import com.kinoko.kidsbankapi.dto.request.AuthenticationRequest
import com.kinoko.kidsbankapi.dto.request.RegistrationRequest
import com.kinoko.kidsbankapi.dto.response.AuthenticationResponse
import com.kinoko.kidsbankapi.entity.AccountEntity
import com.kinoko.kidsbankapi.entity.UserEntity
import com.kinoko.kidsbankapi.enums.Role
import com.kinoko.kidsbankapi.exception.AuthenticationException
import com.kinoko.kidsbankapi.exception.UserNotFoundException
import com.kinoko.kidsbankapi.repository.AccountRepository
import com.kinoko.kidsbankapi.repository.UserRepository
import com.kinoko.kidsbankapi.util.toUserDTO
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
import org.springframework.security.core.AuthenticationException as SpringAuthenticationException

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
            throw AuthenticationException("Поля логин и/или пароль пустые")
        }

        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    request.login,
                    request.password
                )
            )
        } catch (_: SpringAuthenticationException) {
            throw AuthenticationException("Неверные логин или пароль")
        }

        val user = userRepository.findByLogin(request.login) ?: throw UserNotFoundException(
            "Пользователь не найден"
        )

        val (accessToken, refreshToken) = jwtService.generateTokens(user)
        setRefreshToken(response, refreshToken)

        logger.debug("User ${user.login} is authorized")
        logger.info("Authorization is successful!")

        return AuthenticationResponse(accessToken, user.toUserDTO())
    }

    @Transactional
    fun registration(request: RegistrationRequest, response: HttpServletResponse): AuthenticationResponse {
        if (!isValidCredentials(request)) {
            logger.error("Заполнены не все данные")
            throw AuthenticationException("Заполнены не все данные")
        }

        if (userRepository.findByLogin(request.username) != null) {
            logger.warn("Registration error: User with ${request.username} already exist")
            throw AuthenticationException("Пользователь с таким логином уже существует")
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
        val (accessToken, refreshToken) = jwtService.generateTokens(user)

        userRepository.save(user)

        val userAccount = AccountEntity(user = user, balance = 0)
        accountRepository.save(userAccount)

        setRefreshToken(response, refreshToken)

        logger.debug("Пользователь с username ${user.login} был создан")
        logger.info("Регистрация прошла успешно")

        return AuthenticationResponse(accessToken, user.toUserDTO())
    }

    fun logout(response: HttpServletResponse): Map<String, String> {
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
            throw AuthenticationException("Токен пуст")
        }

        val user = userRepository.findByLogin(jwtService.getLogin(userToken))
            ?: throw UserNotFoundException("Пользователь не найден")

        val (accessToken, refreshToken) = jwtService.generateTokens(user)

        setRefreshToken(response, refreshToken)

        logger.debug("Токен пользователя ${user.login} обновлен")

        return AuthenticationResponse(accessToken, user.toUserDTO())
    }

    fun whoAmI(token: String): User {
        val userEntity = userRepository.findByLogin(
            jwtService.getLogin(token)
        ) ?: throw UserNotFoundException("Пользователь не найден")

        val user = userEntity.toUserDTO()
        logger.info("WhoAmI для пользователя ${user.username} был возвращен")
        return user
    }

    private fun setRefreshToken(response: HttpServletResponse, token: String) {
        val cookie = ResponseCookie.from("refreshToken",  "Bearer_$token")
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
