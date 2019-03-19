package cafe.adriel.chucknorrisfacts.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import cafe.adriel.chucknorrisfacts.BuildConfig

inline fun debug(body: () -> Unit) {
    if (BuildConfig.DEBUG) body()
}

inline fun <reified T> javaClass(): Class<T> = T::class.java

inline fun <reified T : Activity> Context.intentFor() = Intent(this, javaClass<T>())
