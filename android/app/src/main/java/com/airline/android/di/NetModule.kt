package com.airline.android.di

import com.airline.android.BuildConfig
import com.airline.android.model.JsendDeserializer
import com.airline.android.model.JsendResponse
import com.airline.android.net.AirlineApi
import com.airline.android.net.AuthenticationInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Network module, that responsible for API interface creation
 */
@Module
class NetModule {
    private val mBaseUrl = "http://${BuildConfig.airlineApiAddress}:8080"

    /**
     * Simple provider for authentication interceptor
     */
    @Provides
    @Singleton
    fun provideAuthInterceptor() = AuthenticationInterceptor()

    /**
     * Provides configured OkHttpClient instance.
     * Adds authentication interceptor
     *
     * @param authenticationInterceptor interceptor
     *
     * @return configured http client
     */
    @Provides
    @Singleton
    fun provideOkHttp(authenticationInterceptor: AuthenticationInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authenticationInterceptor)
            .build()

    /**
     * Provides configured Gson instance.
     * Adds custom type adapter to gson.
     *
     * @return gson instance
     */
    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .registerTypeAdapter(JsendResponse::class.java, JsendDeserializer())
            .create()

    /**
     * Provides configured Retrofit instance.
     * Adds a gson as converter.
     *
     * @param gson configured [Gson] instance with type adapters:
     * - [JsendDeserializer] used to convert [JsendResponse]
     * @param httpClient configured [OkHttpClient] instance
     *
     * @return retrofit instance
     */
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .baseUrl(mBaseUrl)
            .build()

    /**
     * Airline API, that can be injected<br>
     * Creates implementation from [AirlineApi]
     * @param retrofit configured Retrofit instance
     *
     * @return airline api instance
     */
    @Provides
    fun provideAirlineApi(retrofit: Retrofit): AirlineApi =
        retrofit.create(AirlineApi::class.java)
}
