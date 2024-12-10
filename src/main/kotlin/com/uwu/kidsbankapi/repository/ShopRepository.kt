package com.uwu.kidsbankapi.repository

import com.uwu.kidsbankapi.entity.ShopEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ShopRepository: CrudRepository<ShopEntity, Int> {
    fun findShopEntityById(id: Int): ShopEntity
}