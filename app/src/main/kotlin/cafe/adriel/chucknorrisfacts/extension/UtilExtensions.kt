package cafe.adriel.chucknorrisfacts.extension

import cafe.adriel.chucknorrisfacts.BuildConfig
import retrofit2.HttpException

fun isDebug() = BuildConfig.DEBUG

inline fun runIfDebug(body: () -> Unit) {
    if (isDebug()) body()
}

inline fun <reified T> javaClass(): Class<T> = T::class.java

fun Throwable.getUserFriendlyMessage(): String = when (this) {
    is HttpException -> {
        when (code()) {
            in 400..499 -> "The app isn't having a good day"
            in 500..599 -> "The server isn't having a good day"
            else -> "Something with the network isn't right"
        }
    }
    else -> message ?: "Unknown error\nOnly Chuck Norris knows what happened"
}
