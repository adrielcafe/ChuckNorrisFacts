package cafe.adriel.chucknorrisfacts.extension

import cafe.adriel.chucknorrisfacts.BuildConfig

inline fun debug(body: () -> Unit) {
    if (BuildConfig.DEBUG) body()
}

inline fun <reified T> javaClass(): Class<T> = T::class.java