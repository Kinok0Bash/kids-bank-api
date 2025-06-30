package com.kinoko.kidsbankapi.service

import com.kinoko.kidsbankapi.dto.response.ChildBalanceResponse
import com.kinoko.kidsbankapi.dto.response.ParentBalanceResponse
import com.kinoko.kidsbankapi.entity.UserEntity
import com.kinoko.kidsbankapi.repository.AccountRepository
import com.kinoko.kidsbankapi.repository.UserRepository
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
