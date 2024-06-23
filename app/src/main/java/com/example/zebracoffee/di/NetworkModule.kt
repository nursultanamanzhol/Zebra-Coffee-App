package com.example.zebracoffee.di


import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.zebracoffee.data.network.local.AuthInterceptor
import com.example.zebracoffee.data.network.remote.api.ZebraCoffeeApi
import com.example.zebracoffee.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            // Опционально: Настройте Chucker, например, с помощью .collector или .maxContentLength(...)
            .build()
    }

    @Provides
    @ZebraCoffeeUrl
    fun provideZebraCoffeeUrl(): String {
        return Constant.BASE_URL
    }

    @Provides
    @BasicUrl
    fun provideBasicUrl(): String {
        return Constant.BASE_URL
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @AuthInterceptorOkHttpClient
    @Provides
    fun provideAuthInterceptorOkHttpClient(
         authInterceptor: AuthInterceptor, // Если authInterceptor необходим, не забудьте его раскомментировать
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
             .addInterceptor(authInterceptor) // Раскомментируйте, если используете authInterceptor
            .addInterceptor(chuckerInterceptor) // Добавляем Chucker как перехватчик
            .addInterceptor(loggingInterceptor) // Логирование
            .build()
    }
    @BasicOkHttpClient
    @Provides
    fun provideBasicOkHttpClient(
        authInterceptor: AuthInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(chuckerInterceptor) // Добавляем Chucker
            .addInterceptor(loggingInterceptor)
            .build()
    }
    @ZebraCoffeeUrl
    @Provides
    @Singleton
    fun getBasicRetrofit(
        @BasicUrl url: String,
        @BasicOkHttpClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun getZebraCoffeeApi(@ZebraCoffeeUrl retrofit: Retrofit): ZebraCoffeeApi {
        return retrofit.create(ZebraCoffeeApi::class.java)
    }


}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicOkHttpClient

@Qualifier
annotation class ZebraCoffeeUrl

@Qualifier
annotation class BasicUrl