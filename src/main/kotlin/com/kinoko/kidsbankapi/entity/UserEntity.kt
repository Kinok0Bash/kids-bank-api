package com.kinoko.kidsbankapi.entity

import com.kinoko.kidsbankapi.enum.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "lastname", nullable = false)
    val lastname: String = "",

    @Column(name = "name", nullable = false)
    val name: String = "",

    @Column(name = "father_name", nullable = false)
    val fatherName: String = "",

    @Column(name = "login", nullable = false, unique = true)
    val login: String = "",

    @Column(name = "password", nullable = false)
    val authPassword: String = "",

    @Column(name = "birth_date", nullable = false)
    val birthDate: LocalDate = LocalDate.now(),

    @Column(name = "city", nullable = false, length = 50)
    val city: String = "",

    @Column(name = "role", nullable = false, length = 10)
    val role: Role = Role.PARENT,

    @OneToOne
    @JoinColumn(name = "child", nullable = true)
    var child: UserEntity? = null

) : UserDetails {
    override fun getUsername() = this.login
    override fun getPassword() = this.authPassword
    override fun getAuthorities() = mutableListOf(SimpleGrantedAuthority(role.toString()))
}
