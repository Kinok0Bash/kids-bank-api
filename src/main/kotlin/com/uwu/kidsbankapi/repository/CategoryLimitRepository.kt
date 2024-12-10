package com.uwu.kidsbankapi.repository

import com.uwu.kidsbankapi.entity.CategoryLimitEntity
import com.uwu.kidsbankapi.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryLimitRepository: CrudRepository<CategoryLimitEntity, Int> {
    fun findAllByChild(child: UserEntity): List<CategoryLimitEntity>
}