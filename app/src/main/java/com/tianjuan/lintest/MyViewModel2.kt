package com.tianjuan.lintest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

/**
 * created by zll on 2023/12/4 14:47
 */
class MyViewModel2 : ViewModel() {
    val data = liveData<String> {
        delay(1000)
        emit("abc")
    }

    private var userId = MutableLiveData<String>()
    val user = userId.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit("abc$id")
        }
    }


    fun getUser(id: String) = liveData<String> {
        val disposable = emitSource(getUser2(id).map {
            "loading"
        })

        try {

            // first fetchUser(id)

            disposable.dispose()

            // update insert(id)

            emitSource(getUser2(id).map {
                "success"
            })

        } catch (e: Exception) {
            emitSource(getUser2(id).map {
                "error: ${e.message}"
            })
        }

    }


    private fun getUser2(id: String): MutableLiveData<String> {
        return MutableLiveData(id)
    }
}