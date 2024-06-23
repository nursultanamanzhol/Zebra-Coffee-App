package com.example.zebracoffee.data.network.local

import android.content.SharedPreferences
import android.util.Log
import com.example.zebracoffee.data.network.remote.api.ZebraCoffeeApi
import com.example.zebracoffee.domain.entity.RefreshTokenBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val api: dagger.Lazy<ZebraCoffeeApi>,
) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPreferences.getString("refresh_token", null)
        val accessToken = sharedPreferences.getString("access_token", null)
        Log.d("AuthInterceptor", "AccessToken $accessToken")
        Log.d("AuthInterceptor", "RefreshToken $token")
        val request = chain.request()

        return if (!token.isNullOrEmpty()) {
            Log.d("AuthInterceptor", token.toString())
            val newRequest = request.newBuilder()
//                .header("Authorization", "Bearer $accessToken")
                .build()
            val response = chain.proceed(newRequest)
            Log.d("AuthInterceptor", response.code.toString())
            if (response.code == 401) {
                Log.d("AuthInterceptor", "Токен истек, попытка обновления...")
                refreshToken(chain, request)
            } else {
                Log.d("AuthInterceptor", "Запрос выполнен успешно с токеном: $accessToken")

                response
            }
        } else {
            Log.d("AuthInterceptor", "Токен отсутствует, обновление...")
            refreshToken(chain, request)
        }
    }

    private fun refreshToken(chain: Interceptor.Chain, request: Request): Response {
        val token = sharedPreferences.getString("refresh_token", null)
        token?.let {
            val refreshTokenBody = RefreshTokenBody(token)
//            val bearerToken = "Bearer $token"

            return try {
//                val call = api.refreshToken(bearerToken, refreshTokenBody)
                val call = api.get().refreshToken(refreshTokenBody)
                val response = call.execute() // Выполнить запрос синхронно

                if (response.isSuccessful) {
                    val newAccessToken = response.body()?.access_token
                    val edit = sharedPreferences.edit()
                    edit.putString("access_token", newAccessToken)
                    edit.apply()

                    Log.d(
                        "AuthInterceptor",
                        "Токен успешно обновлен. Новый токен доступа: $newAccessToken"
                    )

                    val newRequest = request.newBuilder()
//                        .header("Authorization", "Bearer $newAccessToken")
                        .build()

                    chain.proceed(newRequest)
                } else {
                    Log.d(
                        "AuthInterceptor",
                        "Не удалось обновить токен. Продолжение с исходным запросом."
                    )
                    chain.proceed(request)
                }
            } catch (e: Exception) {
                Log.e(
                    "AuthInterceptor",
                    "Не удалось обновить токен из-за исключения. Продолжение с исходным запросом.",
                    e
                )
                chain.proceed(request)
            }
        } ?: run {
            Log.d(
                "AuthInterceptor",
                "Отсутствует токен обновления. Продолжение с исходным запросом."
            )
            return chain.proceed(request)
        }
    }

}
