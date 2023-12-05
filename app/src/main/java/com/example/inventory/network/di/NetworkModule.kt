package com.example.inventory.network.di

import android.content.Context
import com.example.inventory.common.AppPreferences
import com.example.inventory.network.MyDiscGolfInventoryDataSource
import com.example.inventory.network.NetworkDataSource
import com.example.inventory.network.resultcall.ResultCallAdapterFactory
import com.example.inventory.network.retrofit.AuthenticationService
import com.example.inventory.network.retrofit.PdgaService
import com.example.inventory.utils.BaseSchedulerProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(
        @ApplicationContext context: Context,
        appPreferences: AppPreferences,
        gson: Gson,
        schedulerProvider: BaseSchedulerProvider
    ): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                // Add X-CSRF-Token and Cookie headers only when the flag is set
                val original = chain.request()
                val requestBuilder = original.newBuilder()

                // Conditionally add headers based on the preference
                if (appPreferences.shouldIncludeLogoutHeader == true) {
                    requestBuilder
                        .header("X-CSRF-Token", "your_token")  // Replace with your actual token
                        .header("Cookie", "session_name=your_sessid")  // Replace with your actual sessid
                }

                val request = requestBuilder
                    .method(original.method, original.body)
                    .build()

                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.pdga.com")
            .client(client)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(schedulerProvider.io()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideAuthenticationService(retrofit: Retrofit): AuthenticationService {
        return retrofit.create(AuthenticationService::class.java)
    }

    @Provides
    fun providePdgaService(retrofit: Retrofit): PdgaService {
        return retrofit.create(PdgaService::class.java)
    }

    @Provides
    fun provideNetworkDataSource(
        @ApplicationContext context: Context,
        appPreferences: AppPreferences,
        gson: Gson,
        authenticationService: AuthenticationService,
        pdgaService: PdgaService
    ): NetworkDataSource {
        return MyDiscGolfInventoryDataSource(authenticationService, pdgaService)
    }
}