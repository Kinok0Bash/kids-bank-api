package com.kinoko.kidsbankapi.repository

import com.kinoko.kidsbankapi.entity.AccountEntity
import com.kinoko.kidsbankapi.entity.TransactionEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository: CrudRepository<TransactionEntity, UUID> {
    fun findAllByFromOrderByTimeDesc(from: AccountEntity): List<TransactionEntity>
}
