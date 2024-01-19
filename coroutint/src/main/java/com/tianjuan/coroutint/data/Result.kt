package com.tianjuan.coroutint.data

import java.lang.Exception

/**
 * created by zll on 2023/12/4 15:36
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

}