package cafe.adriel.chucknorrisfacts

import android.app.Application
import android.os.StrictMode
import cafe.adriel.chucknorrisfacts.di.AppComponent
import cafe.adriel.chucknorrisfacts.extension.debug
import com.github.ajalt.timberkt.Timber
import com.pacoworks.rxpaper2.RxPaperBook
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) return
        LeakCanary.install(this)

        debug {
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
            androidLogger(if (BuildConfig.RELEASE) Level.ERROR else Level.DEBUG)
            androidContext(applicationContext)
            modules(AppComponent.getModules())
        }

        RxPaperBook.init(this)
    }

}