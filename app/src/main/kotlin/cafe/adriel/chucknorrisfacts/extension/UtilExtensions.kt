package cafe.adriel.chucknorrisfacts.extension

import cafe.adriel.chucknorrisfacts.BuildConfig
import retrofit2.HttpException

inline fun ifDebug(body: () -> Unit) {
    if (BuildConfig.DEBUG) body()
}

inline fun <reified T> javaClass(): Class<T> = T::class.java

fun Throwable.getUserFriendlyMessage(): String = when (this) {
    is HttpException -> {
        when (code()) {
            in 400..499 -> "Hey pal, are you connected?"
            in 500..599 -> "The server isn't having a good day"
            else -> "Something with the network isn't right"
        }
    }
    else -> message ?: "Unknown error\nOnly Chuck Norris knows what happened"
}
