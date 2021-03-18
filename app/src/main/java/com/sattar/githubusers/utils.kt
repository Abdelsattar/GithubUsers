package com.sattar.githubusers

/**
 * Created by Sattar on 18.03.21.
 */
class utils {
}

fun <T> lazyThreadSafetyNone(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)