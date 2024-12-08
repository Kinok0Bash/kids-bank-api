package com.uwu.kidsbankapi.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "accounts")
data class AccountEntity(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    val user: UserEntity = UserEntity(),

    @Column(name = "balance", nullable = false)
    val balance: Int = 0
)
