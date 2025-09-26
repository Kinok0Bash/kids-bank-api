package com.kinoko.kidsbankapi.util

import com.kinoko.kidsbankapi.dto.Limit
import com.kinoko.kidsbankapi.dto.User
import com.kinoko.kidsbankapi.entity.CategoryLimitEntity
import com.kinoko.kidsbankapi.entity.ShopCategoryEntity
import com.kinoko.kidsbankapi.entity.UserEntity
import com.kinoko.kidsbankapi.enums.Role

fun UserEntity.toUserDTO(): User {
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

fun ShopCategoryEntity.toLimitDTO() = Limit(
    id = this.id,
    name = this.name,
    isLimit = false
)

fun Limit.toCategoryLimitEntity(child: UserEntity) = CategoryLimitEntity(
    category = ShopCategoryEntity(this.id, this.name),
    child = child
)
