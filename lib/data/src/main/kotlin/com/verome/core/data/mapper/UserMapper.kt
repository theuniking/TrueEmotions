package com.verome.core.data.mapper

import com.verome.core.data.local.model.UserEntity
import com.verome.core.domain.model.User
import com.verome.core.domain.model.User.Companion.NULL_USER_ID

fun UserEntity.toUser(): User {
    return User(
        email = email,
        name = name,
        password = password,
        avatar = avatar,
        id = id ?: NULL_USER_ID,
    )
}