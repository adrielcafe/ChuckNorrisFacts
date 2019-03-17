package cafe.adriel.chucknorrisfacts.repository

import cafe.adriel.chucknorrisfacts.BuildConfig
import cafe.adriel.chucknorrisfacts.extension.javaClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceFactory {

    val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    val jsonConverter: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    private val loggingInterceptor: Interceptor by lazy {
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.RELEASE) HttpLoggingInterceptor.Level.NONE
            else HttpLoggingInterceptor.Level.BODY
        )
    }

    inline fun <reified T> newInstance(baseUrl: String): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(jsonConverter))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(javaClass<T>())

}