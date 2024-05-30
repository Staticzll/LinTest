package com.lin.coroutint.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

/**
 * created by zll on 2023/12/4 15:38
 */
class LoginRepository(private val responseParser: LoginResponseParser) {
    companion object {
        private const val loginUrl = "https://example.com/login"
    }

    suspend fun makeLoginRequest(jsonBody: String): Result<LoginResponse> {
        withContext(Dispatchers.IO) {
            val url = URL(loginUrl)
            (url.openConnection() as? HttpURLConnection)?.run {
                requestMethod = "post"
                setRequestProperty("Content-Type", "application/json; utf-8")
                setRequestProperty("Accept", "application/json")
                doOutput = true
                outputStream.write(jsonBody.toByteArray())
                Result.Success(responseParser.parse(inputStream))
            }
        }
        return Result.Error(Exception("Cannot open HttpURLConnection"))
    }
}