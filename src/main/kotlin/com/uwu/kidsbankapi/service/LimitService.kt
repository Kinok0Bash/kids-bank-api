package com.uwu.kidsbankapi.service

import com.uwu.kidsbankapi.dto.Limit
import com.uwu.kidsbankapi.repository.CategoryLimitRepository
import com.uwu.kidsbankapi.repository.ShopCategoryRepository
import com.uwu.kidsbankapi.repository.UserRepository
import com.uwu.kidsbankapi.util.convertToCategoryLimitEntity
import com.uwu.kidsbankapi.util.convertToLimitDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

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
        val userLimitList = categoryLimitRepository.findAllByChild(userRepository.findByLogin(jwtService.extractUsername(token)))
        val limitList = mutableListOf<Limit>()

        categories.forEach { category -> if (category.id != 0) limitList.add(category.convertToLimitDTO()) }

        userLimitList.forEach { userLimit ->
            if (limitList[userLimit.category.id - 1].id != categories[userLimit.category.id].id) {
                logger.error("Не сходятся id ограничений")
                throw Exception("Не сходятся id ограничений")
            }
            limitList[userLimit.category.id - 1].isLimit = true
        }

        return limitList
    }

    fun setUserLimitList(token: String, request: List<Limit>): MutableList<Limit> {
        isGetChild(token)
        val userLimitList = categoryLimitRepository.findAllByChild(userRepository.findByLogin(jwtService.extractUsername(token)))

        userLimitList.forEach { userLimit ->
            categoryLimitRepository.delete(userLimit)
        }

        request.forEach { limit ->
            if (limit.isLimit) categoryLimitRepository.save(limit.convertToCategoryLimitEntity(
                userRepository.findByLogin(jwtService.extractUsername(token)).child!!)
            )
        }

        return getUserLimitList(token)
    }

    private fun isGetChild(token: String) {
        if (userRepository.findByLogin(jwtService.extractUsername(token)).child == null) {
            logger.warn("У пользователя нет ребенка")
            throw Exception("У пользователя нет ребенка")
        }
    }

}