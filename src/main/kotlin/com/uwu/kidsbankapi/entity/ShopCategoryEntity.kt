package com.uwu.kidsbankapi.entity

import jakarta.persistence.*

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
