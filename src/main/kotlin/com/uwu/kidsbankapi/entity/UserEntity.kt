package com.uwu.kidsbankapi.entity

import com.uwu.kidsbankapi.enum.Role
import jakarta.persistence.*
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

    @Column(name = "username", nullable = false, unique = true)
    val login: String = "",

    @Column(name = "password", nullable = false)
    val authPassword: String = "",

    @Column(name = "birth_date", nullable = false)
    val birthDate: LocalDate = LocalDate.now(),

    @Column(name = "city", nullable = false, length = 50)
    val city: String = "",

    @Column(name = "role", nullable = false, length = 10)
    val role: Role = Role.PARENT
) : UserDetails {
    override fun getUsername() = this.login
    override fun getPassword() = this.authPassword
    override fun getAuthorities() = mutableListOf(SimpleGrantedAuthority(role.toString()))
}
