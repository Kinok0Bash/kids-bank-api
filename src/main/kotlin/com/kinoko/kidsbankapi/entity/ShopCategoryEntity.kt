package com.kinoko.kidsbankapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "shop_categories")
data class ShopCategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    val id: Int = 0,

    @Column(name = "name", nullable = false, unique = true)
    val name: String = ""
)
