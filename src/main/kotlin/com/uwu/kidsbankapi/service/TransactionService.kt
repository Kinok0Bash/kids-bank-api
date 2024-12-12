package com.uwu.kidsbankapi.service

import com.uwu.kidsbankapi.dto.Transaction
import com.uwu.kidsbankapi.dto.request.PayRequest
import com.uwu.kidsbankapi.dto.response.TransactionResponse
import com.uwu.kidsbankapi.entity.AccountEntity
import com.uwu.kidsbankapi.entity.ShopEntity
import com.uwu.kidsbankapi.entity.TransactionEntity
import com.uwu.kidsbankapi.enum.TransactionStatus
import com.uwu.kidsbankapi.repository.*
import com.uwu.kidsbankapi.util.convertToTransactionDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.sql.Date
import java.time.LocalDate

@Service
class TransactionService(
    private val jwtService: JwtService,
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val shopRepository: ShopRepository,
    private val categoryLimitRepository: CategoryLimitRepository
) {
    private val logger = LoggerFactory.getLogger(TransactionService::class.java)

    fun getLastTransactions(token: String): MutableList<Transaction> {
        val transactionEntities = transactionRepository.findAllByFromOrderByTimeDesc(
            accountRepository.findAccountEntityByUser(
                userRepository.findByLogin(jwtService.extractUsername(token)).child!!
            )
        )
        val transactions = mutableListOf<Transaction>()

        for (index in 0..<5) {
            transactions.add(transactionEntities[index].convertToTransactionDTO())
        }

        logger.info("Список последних пяти для пользователя ${jwtService.extractUsername(token)} получен")
        return transactions
    }

    fun getAllTransactions(token: String): MutableList<Transaction> {
        val transactionEntities = transactionRepository.findAllByFromOrderByTimeDesc(
            accountRepository.findAccountEntityByUser(
                userRepository.findByLogin(jwtService.extractUsername(token)).child!!
            )
        )
        val transactions = mutableListOf<Transaction>()
        transactionEntities.forEach { transactionEntity -> transactions.add(transactionEntity.convertToTransactionDTO()) }

        logger.info("Список транзакций для пользователя ${jwtService.extractUsername(token)} получен")
        return transactions
    }

    fun transfer(token: String, sum: Int): TransactionResponse {
        val parentAccount =
            accountRepository.findAccountEntityByUser(userRepository.findByLogin(jwtService.extractUsername(token)))
        if (userRepository.findByLogin(jwtService.extractUsername(token)).child == null || sum > parentAccount.balance) {
            logger.warn("У пользователя нет ребенка, либо не хватает средств на счету")
            return TransactionResponse(TransactionStatus.FAIL)
        }

        val childAccount =
            accountRepository.findAccountEntityByUser(userRepository.findByLogin(jwtService.extractUsername(token)).child!!)
        childAccount.balance += sum

        return createTransaction(token, parentAccount, shopRepository.findShopEntityById(0), sum)
    }

    fun pay(token: String, request: PayRequest) = createTransaction(
        token,
        accountRepository.findAccountEntityByUser(userRepository.findByLogin(jwtService.extractUsername(token))),
        shopRepository.findShopEntityById(request.shopId),
        request.sum
    )

    private fun createTransaction(token: String, from: AccountEntity, to: ShopEntity, sum: Int): TransactionResponse {
        if (sum > from.balance) {
            logger.warn("У пользователя недостаточно средств для совершения транзакции")
            return TransactionResponse(TransactionStatus.FAIL)
        }

        // Проверка на ограничение
        categoryLimitRepository.findAllByChild(userRepository.findByLogin(jwtService.extractUsername(token)))
            .forEach { categoryLimitEntity ->
                if (categoryLimitEntity.category.id == to.category.id) {
                    logger.warn("Данная транзакция запрещена ограничением")
                    return TransactionResponse(TransactionStatus.FORBIDDEN)
                }
            }


        from.balance -= sum

        val transaction = TransactionEntity(
            from = from,
            to = to,
            time = Date.valueOf(LocalDate.now()),
            sum = sum
        )
        transactionRepository.save(transaction)
        logger.info("Транзакция прошла успешно")
        return TransactionResponse(TransactionStatus.OK)
    }

}