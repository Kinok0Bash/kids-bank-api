package com.kinoko.kidsbankapi.repository

import com.kinoko.kidsbankapi.entity.ShopEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ShopRepository: CrudRepository<ShopEntity, Int> {
    fun findShopEntityById(id: Int): ShopEntity
}
