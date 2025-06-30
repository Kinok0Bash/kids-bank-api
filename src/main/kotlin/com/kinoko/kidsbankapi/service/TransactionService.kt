package com.kinoko.kidsbankapi.service

import com.kinoko.kidsbankapi.dto.Transaction
import com.kinoko.kidsbankapi.dto.request.PayRequest
import com.kinoko.kidsbankapi.dto.response.TransactionResponse
import com.kinoko.kidsbankapi.entity.AccountEntity
import com.kinoko.kidsbankapi.entity.ShopEntity
import com.kinoko.kidsbankapi.entity.TransactionEntity
import com.kinoko.kidsbankapi.enum.Role
import com.kinoko.kidsbankapi.enum.TransactionStatus
import com.kinoko.kidsbankapi.repository.AccountRepository
import com.kinoko.kidsbankapi.repository.CategoryLimitRepository
import com.kinoko.kidsbankapi.repository.ShopRepository
import com.kinoko.kidsbankapi.repository.TransactionRepository
import com.kinoko.kidsbankapi.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

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

    fun getLastTransactions(token: String): List<Transaction> {
        val transactions = getTransactions(token).map {
            Transaction(
                name = it.to.name,
                category = it.to.category.name,
                sum = it.sum,
                date = it.time
            )
        }
            .sortedByDescending { it.date }

        logger.info("Список последних пяти для пользователя ${jwtService.extractUsername(token)} получен")
        return transactions
    }

    fun getAllTransactions(token: String): MutableList<Transaction> {
        val transactionEntities = getTransactions(token)
        val transactions = mutableListOf<Transaction>()

        transactionEntities.forEach { transactionEntity ->
            transactions.add(
                Transaction(
                    name = transactionEntity.to.name,
                    category = transactionEntity.to.category.name,
                    sum = transactionEntity.sum,
                    date = transactionEntity.time
                )
            )
        }

        logger.info("Список транзакций для пользователя ${jwtService.extractUsername(token)} получен")
        return transactions
    }

    fun getTransactions(token: String): List<TransactionEntity> {
        val userEntity = userRepository.findByLogin(jwtService.extractUsername(token))
        return if (userEntity.role == Role.PARENT && userEntity.child != null) transactionRepository.findAllByFromOrderByTimeDesc(
            accountRepository.findAccountEntityByUser(userEntity.child!!)
        ) else if (userEntity.role == Role.CHILD) transactionRepository.findAllByFromOrderByTimeDesc(
            accountRepository.findAccountEntityByUser(userEntity)
        ) else throw Exception("У пользователя нет ребенка, либо произошла другая неведомая ошибка")
    }

    fun transfer(token: String, sum: Int): TransactionResponse {
        val parentAccount =
            accountRepository.findAccountEntityByUser(userRepository.findByLogin(jwtService.extractUsername(token)))
        if (userRepository.findByLogin(jwtService.extractUsername(token)).child == null || sum > parentAccount.balance) {
            logger.warn("У пользователя нет ребенка, либо не хватает средств на счету")
            return TransactionResponse(TransactionStatus.FAIL, -1)
        }

        val childAccount =
            accountRepository.findAccountEntityByUser(userRepository.findByLogin(jwtService.extractUsername(token)).child!!)
        childAccount.balance += 2 * sum
        parentAccount.balance -= sum

        return createTransaction(token, childAccount, shopRepository.findShopEntityById(0), sum)
    }

    fun pay(token: String, request: PayRequest): TransactionResponse = if (request.sum <= 0 || request.shopId == 0) {
        logger.warn("Входящий запрос не валиден")
        TransactionResponse(TransactionStatus.FAIL, -1)
    } else createTransaction(
        token,
        accountRepository.findAccountEntityByUser(userRepository.findByLogin(jwtService.extractUsername(token))),
        shopRepository.findShopEntityById(request.shopId),
        request.sum
    )

    private fun createTransaction(token: String, from: AccountEntity, to: ShopEntity, sum: Int): TransactionResponse {
        if (sum > from.balance) {
            logger.warn("У пользователя недостаточно средств для совершения транзакции")
            return TransactionResponse(TransactionStatus.FAIL, -1)
        }

        // Проверка на ограничение
        categoryLimitRepository.findAllByChild(userRepository.findByLogin(jwtService.extractUsername(token)))
            .forEach { categoryLimitEntity ->
                if (categoryLimitEntity.category.id == to.category.id) {
                    logger.warn("Данная транзакция запрещена ограничением")
                    return TransactionResponse(TransactionStatus.FORBIDDEN, -1)
                }
            }

        from.balance -= sum
        val transaction = TransactionEntity(
            from = from,
            to = to,
            time = LocalDateTime.now(),
            sum = sum
        )
        transactionRepository.save(transaction)
        logger.info("Транзакция прошла успешно")
        return TransactionResponse(TransactionStatus.OK, sum)
    }

}
