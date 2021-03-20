package com.sattar.githubusers

class utils {
}

 const val KEY_USER = "user"

fun <T> lazyThreadSafetyNone(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)