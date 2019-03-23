package cafe.adriel.chucknorrisfacts

import android.app.Application
import android.content.Context
import android.os.StrictMode
import androidx.test.core.app.ApplicationProvider
import cafe.adriel.chucknorrisfacts.mock.MockFactsService
import cafe.adriel.chucknorrisfacts.repository.ServiceFactory
import com.pacoworks.rxpaper2.RxPaperBook
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
abstract class BaseTest : AutoCloseKoinTest() {

    init {
        // Reset Strict Mode during tests
        StrictMode.enableDefaults()

        // Set mock interceptor to handle API responses
        ServiceFactory.mockInterceptors += MockFactsService.mockInterceptor
    }

    protected val appContext: Context by lazy {
        ApplicationProvider.getApplicationContext<Application>()
    }

    @Before
    open fun setUp() {
        // Add RxJava idling resources
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("RxJava Computation Scheduler"))
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("RxJava IO Scheduler"))
        RxJavaPlugins.setInitSingleSchedulerHandler(Rx2Idler.create("RxJava Single Scheduler"))
        RxJavaPlugins.setInitNewThreadSchedulerHandler(Rx2Idler.create("RxJava NewThread Scheduler"))

        // Clear local DB
        RxPaperBook.with().destroy().blockingAwait()
    }

    @After
    open fun tearDown() {
    }
}
