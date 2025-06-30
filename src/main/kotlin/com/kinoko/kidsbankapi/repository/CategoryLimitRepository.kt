package com.kinoko.kidsbankapi.repository

import com.kinoko.kidsbankapi.entity.CategoryLimitEntity
import com.kinoko.kidsbankapi.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryLimitRepository: CrudRepository<CategoryLimitEntity, Int> {
    fun findAllByChild(child: UserEntity): List<CategoryLimitEntity>
}
