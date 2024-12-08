package com.uwu.kidsbankapi.service

import com.uwu.kidsbankapi.dto.User
import com.uwu.kidsbankapi.dto.request.AuthenticationRequest
import com.uwu.kidsbankapi.dto.request.RegistrationRequest
import com.uwu.kidsbankapi.dto.response.AuthenticationResponse
import com.uwu.kidsbankapi.entity.UserEntity
import com.uwu.kidsbankapi.enum.Role
import com.uwu.kidsbankapi.repository.UserRepository
import com.uwu.kidsbankapi.util.convertToUserDTO
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
    private val passwordEncoder: PasswordEncoder
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
            logger.error("Data is empty")
            throw Exception("Заполнены не все данные!!!")
        }

        val usernames = userRepository.findAllUsernames()

        usernames.forEach { username ->
            if (request.username == username) {
                logger.error("Error of registration: User with username ${request.username} is already exist")
                throw Exception("Пользователь с таким username уже существует")
            }
        }

        val user = UserEntity (
            login = request.username,
            authPassword = passwordEncoder.encode(request.password),
            lastname = request.lastname,
            name = request.lastname,
            fatherName = request.fatherName,
            birthDate = request.birthDate,
            city = request.city,
            role = Role.PARENT
        )
        val tokens = jwtService.generateTokens(user)

        userRepository.save(user)
        setRefreshToken(response, tokens[1])

        logger.debug("User with username ${user.login} has been created")
        logger.info("Registration is successful!")

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
            logger.error("Token is empty")
            throw Exception("Token is empty")
        }

        val user = userRepository.findByLogin(jwtService.extractUsername(userToken))
        val tokens = jwtService.generateTokens(user)

        userRepository.save(user)
        setRefreshToken(response, tokens[1])

        logger.debug("Token of user ${user.login} is refreshed")

        return AuthenticationResponse(tokens[0], user.convertToUserDTO())
    }

    fun whoAmI(token: String): User {
        val userEntity = userRepository.findByLogin(jwtService.extractUsername(token.substring(7)))
        val user = User(
            lastname = userEntity.lastname,
            name = userEntity.name,
            fatherName = userEntity.fatherName,
            username = userEntity.login,
            birthDate = userEntity.birthDate,
            city = userEntity.city,
            role = userEntity.role
        )
        logger.info("WhoAmI for user ${user.username} has been returned")
        return user
    }

    private fun setRefreshToken(response: HttpServletResponse, token: String) {
        val cookie = ResponseCookie.from("refreshToken", token)
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