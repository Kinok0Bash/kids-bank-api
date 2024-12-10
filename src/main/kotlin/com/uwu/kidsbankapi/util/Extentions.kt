package com.uwu.kidsbankapi.util

import com.uwu.kidsbankapi.dto.Limit
import com.uwu.kidsbankapi.dto.Transaction
import com.uwu.kidsbankapi.dto.User
import com.uwu.kidsbankapi.entity.CategoryLimitEntity
import com.uwu.kidsbankapi.entity.ShopCategoryEntity
import com.uwu.kidsbankapi.entity.TransactionEntity
import com.uwu.kidsbankapi.entity.UserEntity
import com.uwu.kidsbankapi.enum.Role
import java.util.*

fun UserEntity.convertToUserDTO(): User {
    val isGetChild = (this.role == Role.PARENT && this.child != null)
    return User(
        fullName = String.format("%s %s %s", this.lastname, this.name, this.fatherName),
        username = this.login,
        lastname = this.lastname,
        name = this.name,
        fatherName = this.fatherName,
        birthDate = this.birthDate,
        city = this.city,
        role = this.role,
        isGetKid = isGetChild
    )
}

fun TransactionEntity.convertToTransactionDTO() = Transaction(
    name = this.to.name,
    category = this.to.category.name,
    sum = this.sum,
    date = Date(this.time.time)
)

fun ShopCategoryEntity.convertToLimitDTO() = Limit(
    id = this.id,
    name = this.name,
    isLimit = false
)

fun Limit.convertToCategoryLimitEntity(child: UserEntity) = CategoryLimitEntity(
    category = ShopCategoryEntity(this.id, this.name),
    child = child
)