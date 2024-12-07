package com.uwu.kidsbankapi.repository

import com.uwu.zooapi.entity.UserEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<UserEntity, Int> {
    fun findByUsername(username: String): UserEntity

    @Query("select u.username from UserEntity u")
    fun findAllUsernames() : List<String>
}