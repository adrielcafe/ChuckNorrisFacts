package cafe.adriel.chucknorrisfacts.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import cafe.adriel.chucknorrisfacts.R
import com.google.android.material.snackbar.Snackbar

inline fun <reified T : Activity> Context.intentFor() = Intent(this, javaClass<T>())

fun Context.isConnected() = getSystemService<ConnectivityManager>()?.activeNetworkInfo?.isConnected ?: false

fun Activity.runIfConnected(showErrorMessage: Boolean = false, body: () -> Unit) {
    if (isConnected()) {
        body()
    } else if (showErrorMessage) {
        Snackbar.make(findViewById(android.R.id.content), R.string.connect_internet, Snackbar.LENGTH_SHORT).show()
    }
}
