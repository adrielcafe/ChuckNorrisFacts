package cafe.adriel.chucknorrisfacts.runner

import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import cafe.adriel.chucknorrisfacts.mock.MockFactService
import cafe.adriel.chucknorrisfacts.repository.ServiceFactory
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins

class TestRunner : AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)
        // Set mock interceptors to handle API responses
        ServiceFactory.mockInterceptors.add(MockFactService.mockInterceptor)

        // Add RxJava idling resources
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("RxJava Computation Scheduler"))
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("RxJava IO Scheduler"))
        RxJavaPlugins.setInitSingleSchedulerHandler(Rx2Idler.create("RxJava Single Scheduler"))
        RxJavaPlugins.setInitNewThreadSchedulerHandler(Rx2Idler.create("RxJava NewThread Scheduler"))
    }

}