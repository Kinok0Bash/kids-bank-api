package com.kinoko.kidsbankapi.repository

import com.kinoko.kidsbankapi.entity.AccountEntity
import com.kinoko.kidsbankapi.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository: CrudRepository<AccountEntity, UUID> {
    fun findAccountEntityByUser(user: UserEntity): AccountEntity
}
