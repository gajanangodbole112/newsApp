package com.gajanan.newsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.gajanan.newsapp.BuildConfig
import com.gajanan.newsapp.database.NewsDatabase
import com.gajanan.newsapp.network.Endpoints
import com.gajanan.newsapp.network.RetrofitApiInterface
import com.gajanan.newsapp.utils.ConnectivityObserver
import com.gajanan.newsapp.utils.ConnectivityObserverImpl
import com.gajanan.newsapp.utils.Constants.CONNECTION_TIMEOUT
import com.gajanan.newsapp.utils.Constants.READ_TIMEOUT
import com.gajanan.newsapp.utils.Constants.WRITE_TIMEOUT
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        val gson = GsonBuilder().serializeNulls().create()

        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(Interceptor { chain ->
                val originalReq = chain.request()
                val newRequest = originalReq
                    .newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.AUTHORIZATION_TOKEN}")
                    .build()
                chain.proceed(newRequest)
            })
            .connectTimeout(
                CONNECTION_TIMEOUT.toLong(),
                TimeUnit.SECONDS
            )
            .readTimeout(
                READ_TIMEOUT.toLong(),
                TimeUnit.SECONDS
            )
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()


        return Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun forApi(retrofit: Retrofit): RetrofitApiInterface =
        retrofit.create(RetrofitApiInterface::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): NewsDatabase =
        Room.databaseBuilder(app , NewsDatabase::class.java,"news_db")
            .build()

    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver {
        return ConnectivityObserverImpl(context)
    }
}
