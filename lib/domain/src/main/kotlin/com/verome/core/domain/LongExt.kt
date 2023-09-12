package com.verome.core.domain

import com.verome.core.domain.model.User.Companion.NULL_USER_ID

fun Long.isNull(): Boolean = this == NULL_USER_ID