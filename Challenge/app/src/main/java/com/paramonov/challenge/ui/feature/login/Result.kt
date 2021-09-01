package com.paramonov.challenge.ui.feature.login

sealed class Result {
    data class Authorization(val isOk: Boolean) : Result()
    data class Error(val error: Throwable) : Result()
}