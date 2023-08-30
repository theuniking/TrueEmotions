package com.verome.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email: String,
    val name: String,
    val password: String,
    val avatar: String? = null,
    val id: Long,
) : Parcelable {
    companion object {
        const val NULL_USER_ID = 0L
    }
}
