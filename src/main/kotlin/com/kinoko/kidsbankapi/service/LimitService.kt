package com.kinoko.kidsbankapi.service

import com.kinoko.kidsbankapi.dto.Limit
import com.kinoko.kidsbankapi.exception.UserNotFoundException
import com.kinoko.kidsbankapi.repository.CategoryLimitRepository
import com.kinoko.kidsbankapi.repository.ShopCategoryRepository
import com.kinoko.kidsbankapi.repository.UserRepository
import com.kinoko.kidsbankapi.util.toCategoryLimitEntity
import com.kinoko.kidsbankapi.util.toLimitDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LimitService(
    private val shopCategoryRepository: ShopCategoryRepository,
    private val categoryLimitRepository: CategoryLimitRepository,
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {
    private val logger = LoggerFactory.getLogger(LimitService::class.java)

    fun getUserLimitList(token: String): MutableList<Limit> {
        isGetChild(token)
        val categories = shopCategoryRepository.findAll()
        val userLimitList = categoryLimitRepository.findAllByChild(
            userRepository.findByLogin(jwtService.getLogin(token))?.child
                ?: throw UserNotFoundException("Пользователь не найден или у пользователя нет ребенка")
        )
        val limitList = mutableListOf<Limit>()

        categories.forEach { category -> if (category.id != 0) limitList.add(category.toLimitDTO()) }

        userLimitList.forEach { userLimit ->
            if (limitList[userLimit.category.id - 1].id != categories[userLimit.category.id].id) {
                logger.error("Не сходятся id ограничений")
                throw Exception("Не сходятся id ограничений")
            }
            limitList[userLimit.category.id - 1].isLimit = true
        }

        return limitList
    }

    @Transactional
    fun setUserLimitList(token: String, request: List<Limit>): MutableList<Limit> {
        isGetChild(token)

        val child = userRepository.findByLogin(jwtService.getLogin(token))?.child
            ?: throw UserNotFoundException("Пользователь не найден или у пользователя нет ребенка")

        val userLimitList = categoryLimitRepository.findAllByChild(child)

        userLimitList.forEach { userLimit ->
            categoryLimitRepository.delete(userLimit)
        }

        request.forEach { limit ->
            if (limit.isLimit) {
                categoryLimitRepository.save(limit.toCategoryLimitEntity(child))
            }
        }

        return getUserLimitList(token)
    }

    private fun isGetChild(token: String) {
        if (userRepository.findByLogin(jwtService.getLogin(token))?.child == null) {
            logger.warn("У пользователя нет ребенка")
            throw UserNotFoundException("Пользователь не найден или у пользователя нет ребенка")
        }
    }

}
