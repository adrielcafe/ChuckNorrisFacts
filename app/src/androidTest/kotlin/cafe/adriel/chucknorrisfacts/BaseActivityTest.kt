package cafe.adriel.chucknorrisfacts

import android.app.Activity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import cafe.adriel.chucknorrisfacts.repository.ServiceFactory
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import com.schibsted.spain.barista.rule.cleardata.ClearFilesRule
import com.schibsted.spain.barista.rule.cleardata.ClearPreferencesRule
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class BaseActivityTest<T : Activity> {

    init {
        // Set mock interceptors to handle API responses
        ServiceFactory.mockInterceptors.add(FactServiceInterceptor.mockInterceptor)

        // Add RxJava idling resources
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("RxJava Computation Scheduler"))
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("RxJava IO Scheduler"))
        RxJavaPlugins.setInitSingleSchedulerHandler(Rx2Idler.create("RxJava Single Scheduler"))
        RxJavaPlugins.setInitNewThreadSchedulerHandler(Rx2Idler.create("RxJava NewThread Scheduler"))
    }

    @get:Rule
    abstract val activityRule: ActivityScenarioRule<T>

    @get:Rule
    val clearPreferencesRule = ClearPreferencesRule()
    @get:Rule
    val clearDatabaseRule = ClearDatabaseRule()
    @get:Rule
    val clearFilesRule = ClearFilesRule()

    @Before
    open fun setUp(){
    }

    @After
    open fun tearDown(){
    }

}