package com.uwu.kidsbankapi.service

import com.uwu.kidsbankapi.dto.response.ChildBalanceResponse
import com.uwu.kidsbankapi.dto.response.ParentBalanceResponse
import com.uwu.kidsbankapi.entity.UserEntity
import com.uwu.kidsbankapi.repository.AccountRepository
import com.uwu.kidsbankapi.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class BalanceService(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {

    fun getParentAccountBalance(token: String): ParentBalanceResponse {
        val user = userRepository.findByLogin(jwtService.extractUsername(token))
        return  if (user.child != null) getParentAccountBalanceWithChild(user)
        else getParentAccountBalanceWithOutChild(user)
    }

    fun getParentAccountBalanceWithChild(user: UserEntity) = ParentBalanceResponse(
        parentBalance = accountRepository.findAccountEntityByUser(user).balance,
        childBalance = accountRepository.findAccountEntityByUser(user.child!!).balance
    )

    fun getParentAccountBalanceWithOutChild(user: UserEntity) = ParentBalanceResponse(
        parentBalance = accountRepository.findAccountEntityByUser(user).balance,
        childBalance = null
    )

    fun getChildAccountBalance(token: String) = ChildBalanceResponse(
        balance = accountRepository.findAccountEntityByUser(userRepository.findByLogin(jwtService.extractUsername(token))).balance
    )

}