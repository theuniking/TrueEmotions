package com.verome.core.domain.error.handling

import com.verome.core.domain.localization.string.VmRes
import com.verome.domain.R

val Throwable.errorMessage: VmRes<CharSequence>
    get() = when (this) {
        is ServerException, is DeserializationException -> VmRes.StrRes(R.string.error_invalid_response)

        is NoServerResponseException -> VmRes.StrRes(R.string.error_no_server_response)

        is NoInternetException -> VmRes.StrRes(R.string.error_no_internet_connection)

        is SslHandshakeException -> VmRes.StrRes(R.string.error_ssl_handshake)

        else -> VmRes.StrRes(R.string.error_unexpected)
    }