package cafe.adriel.chucknorrisfacts

import android.app.Application
import android.os.StrictMode
import cafe.adriel.chucknorrisfacts.di.AppComponent
import cafe.adriel.chucknorrisfacts.extension.isDebug
import cafe.adriel.chucknorrisfacts.extension.runIfDebug
import com.github.ajalt.timberkt.Timber
import com.pacoworks.rxpaper2.RxPaperBook
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) return
        if (LeakCanary.installedRefWatcher() == RefWatcher.DISABLED) LeakCanary.install(this)

        runIfDebug {
            Timber.plant(Timber.DebugTree())

            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .permitDiskReads()
                    .penaltyLog()
                    .penaltyDropBox()
                    .penaltyDeath()
                    .build()
            )
        }

        startKoin {
            androidLogger(if (isDebug()) Level.DEBUG else Level.ERROR)
            androidContext(applicationContext)
            modules(AppComponent(applicationContext).getModules())
        }

        RxPaperBook.init(this)
    }
}
