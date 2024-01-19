package com.tianjuan.coroutint.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * created by zll on 2023/12/4 17:25
 */
class ExampleClass {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun exampleMethod() {
        val job = scope.launch {
            //fetchDocs()
        }

        job.cancel()
    }

    fun cancelUp() {
        scope.cancel()
    }

    class IntTransformer : (Int, Int) -> Int {
        override fun invoke(p1: Int, p2: Int) = 1


    }

    val intFunction: (Int, Int) -> Int = IntTransformer()


    val d = intFunction(1, 2)

    fun d2(intFunction2: (Int, Int) -> Int) {
        intFunction2(2, 1)
    }

    fun d33() {
        d2(IntTransformer())
    }


    val isEmptyStringList: List<String>.() -> Boolean = List<String>::isEmpty
    val isEmptyStringList2: (List<String>) -> Boolean = List<String>::isEmpty
}