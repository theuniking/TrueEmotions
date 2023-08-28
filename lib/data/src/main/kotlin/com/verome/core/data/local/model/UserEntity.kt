package com.verome.core.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    val email: String,
    val name: String,
    val password: String,
    val avatar: String? = null,
    @PrimaryKey val id: Long? = null,
)