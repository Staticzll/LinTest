package com.tianjuan.coroutint.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tianjuan.coroutint.data.LoginRepository
import com.tianjuan.coroutint.data.LoginResponse
import com.tianjuan.coroutint.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * created by zll on 2023/12/4 15:48
 */
class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    fun login(username: String, token: String) {
        viewModelScope.launch() {
            val jsonBody = "{username:\"$username\",token:\"$token\"}"
            val result = try {
                loginRepository.makeLoginRequest(jsonBody)
            } catch (e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            when (result) {
                is Result<LoginResponse> -> "abc"
                else -> ""
            }
        }
    }
}