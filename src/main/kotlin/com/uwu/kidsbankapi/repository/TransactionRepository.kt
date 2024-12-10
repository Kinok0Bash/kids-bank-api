package com.uwu.kidsbankapi.repository

import com.uwu.kidsbankapi.entity.AccountEntity
import com.uwu.kidsbankapi.entity.TransactionEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionRepository: CrudRepository<TransactionEntity, UUID> {
    fun findAllByFromOrderByTimeDesc(from: AccountEntity): List<TransactionEntity>
}