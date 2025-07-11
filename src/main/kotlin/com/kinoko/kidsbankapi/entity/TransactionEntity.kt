package com.kinoko.kidsbankapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "transactions")
data class TransactionEntity(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "sender", nullable = false)
    val from: AccountEntity = AccountEntity(),

    @ManyToOne
    @JoinColumn(name = "recipient", nullable = false)
    val to: ShopEntity = ShopEntity(),

    @Column(name = "time", nullable = false)
    val time: LocalDateTime = LocalDateTime.now(),

    @Column(name = "sum", nullable = false)
    val sum: Int = 0
)
