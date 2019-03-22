package cafe.adriel.chucknorrisfacts

import android.app.Application
import android.content.Context
import android.os.StrictMode
import androidx.test.core.app.ApplicationProvider
import cafe.adriel.chucknorrisfacts.mock.MockFactsService
import cafe.adriel.chucknorrisfacts.repository.ServiceFactory
import com.pacoworks.rxpaper2.RxPaperBook
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
abstract class BaseTest : AutoCloseKoinTest() {

    protected val appContext: Context by lazy {
        ApplicationProvider.getApplicationContext<Application>()
    }

    @Before
    open fun setUp() {
        // Reset Strict Mode during tests
        StrictMode.enableDefaults()

        // Set mock interceptor to handle API responses
        ServiceFactory.mockInterceptors.add(MockFactsService.mockInterceptor)

        // Clear local DB
        RxPaperBook.with().destroy().blockingAwait()
    }

    @After
    open fun tearDown() {
    }
}
