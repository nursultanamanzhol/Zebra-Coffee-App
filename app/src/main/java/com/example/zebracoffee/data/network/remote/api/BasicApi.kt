//package com.example.zebracoffee.data.network.remote.api
//
//import com.example.zebracoffee.domain.entity.RefreshTokenBody
//import com.example.zebracoffee.domain.entity.RefreshTokenResponse
//import retrofit2.Call
//import retrofit2.Response
//import retrofit2.http.Body
//import retrofit2.http.POST
//
//interface BasicApi {
//    @POST("user/mobile/token/refresh/")
//    fun refreshToken(
//        @Body body: RefreshTokenBody,
//    ): Call<RefreshTokenResponse>
//}