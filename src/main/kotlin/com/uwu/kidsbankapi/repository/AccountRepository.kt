package com.uwu.kidsbankapi.repository

import com.uwu.kidsbankapi.entity.AccountEntity
import com.uwu.kidsbankapi.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AccountRepository: CrudRepository<AccountEntity, UUID> {
    fun findAccountEntityByUser(user: UserEntity): AccountEntity
}