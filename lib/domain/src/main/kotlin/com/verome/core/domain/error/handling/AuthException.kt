package com.verome.core.domain.error.handling

/**
 * Имя пользователя меньше чем 2 символа
 */
class NameTooShortException(cause: Throwable?) : ApplicationException(cause)

/**
 * Имя пользователя больше чем 16 символов
 */
class NameTooLongException(cause: Throwable?) : ApplicationException(cause)

/**
 * Пароль меньше 6 символов
 */
class PasswordTooShort(cause: Throwable?) : ApplicationException(cause)

/**
 * E-mail не подходит по regex
 */
class InvalidEmail(cause: Throwable?) : ApplicationException(cause)

/**
 * E-mail не найден в базе
 */
class NoSuchUser(cause: Throwable?) : ApplicationException(cause)