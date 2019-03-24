package cafe.adriel.chucknorrisfacts.repository

import cafe.adriel.chucknorrisfacts.extension.isDebug
import cafe.adriel.chucknorrisfacts.extension.javaClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ServiceFactory {

    private val loggingInterceptor: Interceptor by lazy {
        HttpLoggingInterceptor().setLevel(
            if (isDebug()) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        )
    }
    val mockInterceptors: MutableSet<Interceptor> by lazy {
        mutableSetOf<Interceptor>()
    }
    val jsonConverter: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .also { builder ->
                // Add mock interceptors, used only during tests
                mockInterceptors.forEach {
                    builder.addInterceptor(it)
                }
            }
            .addInterceptor(loggingInterceptor)
            // Chuck Norris Facts API can be very slow sometimes
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    inline fun <reified T> newInstance(baseUrl: String): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(jsonConverter))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(javaClass())
    }
}
