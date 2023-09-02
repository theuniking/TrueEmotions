package com.verome.core.data.repository

import com.verome.core.domain.error.handling.InvalidEmail
import com.verome.core.domain.error.handling.NameTooLongException
import com.verome.core.domain.error.handling.NameTooShortException
import com.verome.core.domain.error.handling.PasswordTooShort
import com.verome.core.domain.repository.AuthValidation

private val MIN_NAME_LENGTH = 2
private val MAX_NAME_LENGTH = 16
private val MIN_PASSWORD_LENGTH = 6
private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

class DefaultAuthValidation : AuthValidation {
    override suspend fun validateNameField(name: String) {
        if (name.length < MIN_NAME_LENGTH) throw NameTooShortException(cause = IllegalArgumentException())
        if (name.length > MAX_NAME_LENGTH) throw NameTooLongException(cause = IllegalArgumentException())
    }
    override suspend fun validateEmailField(email: String) {
        if (!email.matches(EMAIL_REGEX.toRegex())) throw InvalidEmail(cause = IllegalArgumentException())
    }
    override suspend fun validatePasswordField(password: String) {
        if (password.length < MIN_PASSWORD_LENGTH) throw PasswordTooShort(cause = IllegalArgumentException())
    }
}