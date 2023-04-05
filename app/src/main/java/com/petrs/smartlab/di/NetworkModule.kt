package com.petrs.smartlab.di

import android.content.Context
import com.petrs.smartlab.data.api.SmartLabApi
import com.petrs.smartlab.utils.network.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://medic.madskill.ru"
private const val RETROFIT = "RETROFIT"

val networkModule = module {
    single { provideNetworkConnectionInterceptor(context = androidContext()) }
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(
        loggingInterceptor = get(),
        networkConnectionInterceptor = get()
    ) }
    single(named(RETROFIT)) { provideRetrofit(client = get()) }
    single { provideSmartLabApi(get(named(RETROFIT))) }
}

private fun provideNetworkConnectionInterceptor(context: Context) =
    NetworkConnectionInterceptor(context = context)

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    networkConnectionInterceptor: NetworkConnectionInterceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(interceptor = networkConnectionInterceptor)
        .addInterceptor(interceptor = loggingInterceptor)
        .writeTimeout(timeout = 5, unit = TimeUnit.MINUTES)
        .readTimeout(timeout = 5, unit = TimeUnit.MINUTES)
        .build()

fun provideRetrofit(client: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

fun provideSmartLabApi(retrofit: Retrofit): SmartLabApi =
    retrofit.create(SmartLabApi::class.java)