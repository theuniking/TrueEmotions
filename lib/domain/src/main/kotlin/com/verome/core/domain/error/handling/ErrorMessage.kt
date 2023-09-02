package com.verome.core.domain.error.handling

import com.verome.core.domain.localization.string.VmRes
import com.verome.domain.R

val Throwable.errorMessage: VmRes<CharSequence>
    get() = when (this) {
        is ServerException, is DeserializationException -> VmRes.StrRes(R.string.error_invalid_response)

        is NoServerResponseException -> VmRes.StrRes(R.string.error_no_server_response)

        is NoInternetException -> VmRes.StrRes(R.string.error_no_internet_connection)

        is SslHandshakeException -> VmRes.StrRes(R.string.error_ssl_handshake)

        is NameTooShortException -> VmRes.StrRes(R.string.error_name_too_short)

        is NameTooLongException -> VmRes.StrRes(R.string.error_name_too_long)

        is PasswordTooShort -> VmRes.StrRes(R.string.error_password_too_short)

        is InvalidEmail -> VmRes.StrRes(R.string.error_email_invalid)

        is NoSuchUser -> VmRes.StrRes(R.string.error_email_not_found)

        else -> VmRes.StrRes(R.string.error_unexpected)
    }