package com.uwu.kidsbankapi.repository

import com.uwu.kidsbankapi.entity.ShopCategoryEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ShopCategoryRepository: CrudRepository<ShopCategoryEntity, Int> {
    override fun findAll(): List<ShopCategoryEntity>
}