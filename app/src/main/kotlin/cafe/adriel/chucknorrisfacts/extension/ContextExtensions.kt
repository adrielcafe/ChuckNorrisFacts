package cafe.adriel.chucknorrisfacts.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.core.content.getSystemService

inline fun <reified T : Activity> Context.intentFor() = Intent(this, javaClass<T>())

fun Context.isConnected() = getSystemService<ConnectivityManager>()?.activeNetworkInfo?.isConnected ?: false