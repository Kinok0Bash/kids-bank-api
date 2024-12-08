package com.uwu.kidsbankapi.entity

import jakarta.persistence.*

@Entity
@Table(name = "shops")
data class ShopEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    val id: Int = 0,

    @Column(name = "name", nullable = false)
    val name: String = "",

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    val category: ShopCategoryEntity = ShopCategoryEntity()
)

