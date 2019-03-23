package cafe.adriel.chucknorrisfacts.repository

import cafe.adriel.chucknorrisfacts.BaseTest
import org.junit.Test
import retrofit2.Call
import retrofit2.http.GET
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isA
import java.net.UnknownHostException

class ServiceFactoryTest : BaseTest() {

    @Test
    fun newInstance_CreateApiService_ReturnValidServiceInstance() {
        val testService = ServiceFactory.newInstance<TestService>(TestService.BASE_URL)
        expectThat(testService).isA<TestService>()
        expectThrows<UnknownHostException> {
            testService.getTest().execute()
        }
    }

    interface TestService {
        companion object {
            const val BASE_URL = "https://test.api/"
        }

        @GET("test")
        fun getTest(): Call<Unit>
    }

}