package com.tianjuan.coroutint.test

/**
 * created by zll on 2023/12/6 17:45
 */
fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
    return { x -> f(g(x)) }
}

fun <A, B> compose2(f: (A) -> B): (A) -> B {
    return { x -> f(x) }
}

fun isOdd(x: Int) = x % 2 != 0

fun main() {
    //sampleStart
    fun length(s: String) = s.length

    val compose2 = compose2(::length)
    println(compose2)

    val oddLength = compose(::isOdd, ::length)
    val strings = listOf("a", "ab", "abc")

    println(strings.filter(oddLength))
//sampleEnd
}