package com.example.zebracoffee.data.network.remote.api

import com.example.zebracoffee.data.modelDto.AvatarImageListDto
import com.example.zebracoffee.data.modelDto.CheckVersionDto
import com.example.zebracoffee.data.modelDto.CoffeeDetails
import com.example.zebracoffee.data.modelDto.CreateReviewBody
import com.example.zebracoffee.data.modelDto.FCMRegistrationTokenDto
import com.example.zebracoffee.data.modelDto.LogoutResponse
import com.example.zebracoffee.data.modelDto.LoyalCardProgressDto
import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import com.example.zebracoffee.data.modelDto.News
import com.example.zebracoffee.data.modelDto.NewsList
import com.example.zebracoffee.data.modelDto.NotificationListDto
import com.example.zebracoffee.data.modelDto.PhoneNumberBody
import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import com.example.zebracoffee.data.modelDto.QrResponseDto
import com.example.zebracoffee.data.modelDto.ReviewList
import com.example.zebracoffee.data.modelDto.ReviewListItem
import com.example.zebracoffee.data.modelDto.ShopCountry
import com.example.zebracoffee.data.modelDto.StoriesCategory
import com.example.zebracoffee.data.modelDto.StoriesList
import com.example.zebracoffee.data.modelDto.UpdateAvatarBodyDto
import com.example.zebracoffee.data.modelDto.UpdateProfileBody
import com.example.zebracoffee.data.modelDto.UpdateUserDataBody
import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.data.modelDto.VerificationCodeBody
import com.example.zebracoffee.data.modelDto.VerificationCodeResponse
import com.example.zebracoffee.data.modelDto.menuDto.MenuDto
import com.example.zebracoffee.domain.entity.RefreshTokenBody
import com.example.zebracoffee.domain.entity.RefreshTokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ZebraCoffeeApi {
    @POST("user/mobile/token/refresh/")
    fun refreshToken(
//        @Header("Authorization") bearerToken: String,
        @Body body: RefreshTokenBody,
    ): Call<RefreshTokenResponse>

    @POST("user/mobile/get_phone_number/")
    suspend fun getPhoneNumber(@Body body: PhoneNumberBody): Response<PhoneNumberResponse>

    @POST("user/mobile/authenticate/")
    suspend fun verifyCode(@Body body: VerificationCodeBody): Response<VerificationCodeResponse>

    @POST("user/mobile/update_profile/")
    suspend fun updateInfoProfile(
        @Body body: UpdateProfileBody,
        @Header("Authorization") bearerToken: String,
    ): Response<PhoneNumberResponse>

    @GET("user/avatar")
    suspend fun getAvatarImage(
        @Header("Authorization") bearerToken: String,
    ): AvatarImageListDto

    @POST("user/mobile/choose_avatar/")
    suspend fun updateAvatar(
        @Body body: UpdateAvatarBodyDto,
        @Header("Authorization") bearerToken: String,
    ): Response<PhoneNumberResponse>

    @GET("user/mobile/get_user_data/")
    suspend fun getUserData(
        @Header("Authorization") bearerToken: String,
    ): Response<UserDto>

    @GET("user/mobile/user_loyalty_card/")
    suspend fun getLoyaltyCardInfo(
        @Header("Authorization") bearerToken: String,
    ): Response<LoyaltyCardResponseDto>

    @GET("mobile/stories/category/")
    suspend fun getStoriesList(
        @Header("Authorization") bearerToken: String,
    ): StoriesList

    @GET("mobile/stories/category/{id}")
    suspend fun getOnBoarding(
        @Path("id") id: Int,
    ): StoriesCategory

    @GET("mobile/news/")
    suspend fun getNewsList(
        @Header("Authorization") bearerToken: String,
    ): NewsList

    @GET("mobile/news/{id}")
    suspend fun getNewsById(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
    ): Response<News>

    @GET("user/mobile/get_user_qr/")
    suspend fun getUserQr(
        @Header("Authorization") bearerToken: String,
    ): Response<QrResponseDto>

    @GET("notifications/")
    suspend fun getNotifications(
        @Header("Authorization") bearerToken: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
    ): NotificationListDto

    @POST("user/mobile/get_device_token/")
    suspend fun setFCMRegistrationToken(
        @Header("Authorization") bearerToken: String,
        @Body request: FCMRegistrationTokenDto
    ): Response<Unit>

    @PUT("user/mobile/update_user_data/")
    suspend fun updateUserData(
        @Body body: UpdateUserDataBody,
        @Header("Authorization") bearerToken: String,
    ): Response<UpdatedUserInfo>

    @GET("user/mobile/user_loyalty_card_progress/")
    suspend fun getLoyaltyCardProgress(
        @Header("Authorization") bearerToken: String,
    ): LoyalCardProgressDto

    @GET("user/mobile/logout/")
    suspend fun logout(
        @Header("Authorization") bearerToken: String,
    ): Response<LogoutResponse>

    @DELETE("user/mobile/delete_user/")
    suspend fun deleteUserAccount(
        @Header("Authorization") bearerToken: String,
    )

    @GET("shop/country/")
    suspend fun getCountryList(
        @Header("Authorization") bearerToken: String,
    ): ShopCountry

    @GET("mobile/shop/")
    suspend fun getCoffeeShopByCity(
        @Header("Authorization") bearerToken: String,
        @Query("city_id") cityId: Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): List<CoffeeDetails>

    @GET("mobile/shop/{id}/")
    suspend fun getCoffeeShopDetails(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Int,
    ): Response<CoffeeDetails>

    @GET("version/check_version/")
    suspend fun checkAppVersion(
        @Query("platform") platform: String,
        @Query("currentVersion") currentVersion: String,
        @Header("Authorization") bearerToken: String,
    ): Response<CheckVersionDto>

    @GET("shop/review/")
    suspend fun getReviewByShopId(
        @Query("shop_id") shopId: Int,
        @Header("Authorization") bearerToken: String,
    ): ReviewList

    @POST("shop/review/")
    suspend fun createNewReview(
        @Body body: CreateReviewBody,
        @Header("Authorization") bearerToken: String,
    ): Response<ReviewListItem>

    @PUT("shop/review/{id}/")
    suspend fun updateReview(
        @Body body: CreateReviewBody,
        @Path("id") id: Int,
        @Header("Authorization") bearerToken: String,
    ): Response<ReviewListItem>

    @GET("mobile/menu/")
    suspend fun getMenuByShopId(
        @Query("shop_id") shopId: Int,
        @Header("Authorization") bearerToken: String,
    ): MenuDto

    @GET("mobile/menu/")
    suspend fun getSearchProduct(
        @Query("search") search: String,
        @Query("shop_id") shopId: Int,
        @Header("Authorization") bearerToken: String
    ): MenuDto

    @GET("mobile/menu/search-history/")
    suspend fun getSearchHistory(
        @Query("shop_id") shopId: Int,
        @Header("Authorization") bearerToken: String
    ): MenuDto
}
