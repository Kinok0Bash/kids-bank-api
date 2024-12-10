package com.uwu.kidsbankapi.service

import com.uwu.kidsbankapi.dto.response.ChildBalanceResponse
import com.uwu.kidsbankapi.dto.response.ParentBalanceResponse
import com.uwu.kidsbankapi.repository.AccountRepository
import com.uwu.kidsbankapi.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class BalanceService(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {

    fun getParentAccountBalance(token: String) = ParentBalanceResponse (
        parentBalance = accountRepository.findAccountEntityByUser(userRepository.findByLogin(jwtService.extractUsername(token))).balance,
        childBalance = accountRepository.findAccountEntityByUser(userRepository.findByLogin(jwtService.extractUsername(token)).child!!).balance
    )

    fun getChildAccountBalance(token: String) = ChildBalanceResponse (
        balance = accountRepository.findAccountEntityByUser(userRepository.findByLogin(jwtService.extractUsername(token))).balance
    )

}