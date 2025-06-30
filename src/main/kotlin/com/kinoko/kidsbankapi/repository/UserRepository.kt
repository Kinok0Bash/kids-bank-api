package com.kinoko.kidsbankapi.repository

import com.kinoko.kidsbankapi.entity.UserEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<UserEntity, UUID> {
    fun findByLogin(login: String): UserEntity

    @Query("select u.login from UserEntity u")
    fun findAllUsernames() : List<String>
}
