package com.kinoko.kidsbankapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "categories_limit")
data class CategoryLimitEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    val category: ShopCategoryEntity = ShopCategoryEntity(),

    @ManyToOne
    @JoinColumn(name = "child", nullable = false)
    val child: UserEntity = UserEntity()
)
