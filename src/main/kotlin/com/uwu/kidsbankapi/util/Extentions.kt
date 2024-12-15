package com.uwu.kidsbankapi.util

import com.uwu.kidsbankapi.dto.Limit
import com.uwu.kidsbankapi.dto.User
import com.uwu.kidsbankapi.entity.CategoryLimitEntity
import com.uwu.kidsbankapi.entity.ShopCategoryEntity
import com.uwu.kidsbankapi.entity.UserEntity
import com.uwu.kidsbankapi.enum.Role

fun UserEntity.convertToUserDTO(): User {
    val isGetChild = (this.role == Role.PARENT && this.child != null)
    return User(
        id = this.id,
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

fun ShopCategoryEntity.convertToLimitDTO() = Limit(
    id = this.id,
    name = this.name,
    isLimit = false
)

fun Limit.convertToCategoryLimitEntity(child: UserEntity) = CategoryLimitEntity(
    category = ShopCategoryEntity(this.id, this.name),
    child = child
)