package com.lin.coroutint.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

/**
 * created by zll on 2023/12/4 16:34
 */
class Test {

    suspend fun fetchDocs() {
        val result = get("https://developer.android.com")
        //show(result)
    }

    suspend fun get(url: String) = withContext(Dispatchers.IO) {}


    suspend fun fetchTwoDocs() {
        coroutineScope {
            val deferredOne = async { }
            val deferredTwo = async { }
            deferredOne.await()
            deferredTwo.await()
        }
    }

    suspend fun fetchTwoDocs2() {
        coroutineScope {
            val deferreds = listOf(
                async { },
                async { })

            deferreds.awaitAll()
        }
    }
}