package com.kinoko.kidsbankapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "accounts")
data class AccountEntity(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity = UserEntity(),

    @Column(name = "balance", nullable = false)
    var balance: Int = 0
)
