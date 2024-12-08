package com.uwu.kidsbankapi.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "transactions")
data class TransactionEntity(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "from", nullable = false)
    val from: AccountEntity = AccountEntity(),

    @ManyToOne
    @JoinColumn(name = "to", nullable = false)
    val to: AccountEntity = AccountEntity(),

    @Column(name = "type", nullable = false, length = 20)
    val type: String = "",

    @Column(name = "sum", nullable = false)
    val sum: Int = 0
)
