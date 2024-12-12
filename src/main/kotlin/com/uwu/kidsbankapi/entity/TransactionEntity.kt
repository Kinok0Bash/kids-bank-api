package com.uwu.kidsbankapi.entity

import jakarta.persistence.*
import java.sql.Date
import java.time.LocalDate
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
    val time: Date = Date.valueOf(LocalDate.now()),

    @Column(name = "sum", nullable = false)
    val sum: Int = 0
)
