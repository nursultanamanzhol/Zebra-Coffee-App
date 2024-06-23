package com.example.zebracoffee.domain.repository

import com.example.zebracoffee.data.modelDto.FCMRegistrationTokenDto
import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import com.example.zebracoffee.data.modelDto.NotificationListDto
import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.data.modelDto.VerificationCodeResponse
import com.example.zebracoffee.domain.entity.AvatarImage
import com.example.zebracoffee.domain.entity.FCMRegistrationToken
import com.example.zebracoffee.domain.entity.Notification
import retrofit2.Response

interface RegistrationRepository {

//    suspend fun refreshToken(/*bearerToken:String,*/refresh: String): Call<RefreshTokenResponse>


//    suspend fun updateToken(
//        bearerToken: String,
//        refreshToken: String
//    ): String? {}

    suspend fun getPhoneNumber(phoneNumber: String): Response<PhoneNumberResponse>
    suspend fun verifyCode(phoneNumber: String, smsCode: Int): Response<VerificationCodeResponse>
    suspend fun getAvatarImages(bearerToken: String): List<AvatarImage>
    suspend fun updateUserData(
        name: String,
        phone: String,
        birthDate: String,
        avatarId: Int,
        bearerToken: String,
    ): Response<UpdatedUserInfo>

    suspend fun getUserData(bearerToken: String): Response<UserDto>

    suspend fun updateAvatarImage(
        avatarChoice: Int,
        bearerToken: String
    ): Response<PhoneNumberResponse>

    suspend fun getLoyaltyCardInfo(bearerToken: String): Response<LoyaltyCardResponseDto>
    suspend fun getNotificationMessages(bearerToken: String, page: Int, limit: Int): NotificationListDto

    suspend fun setFCMRegistrationToken(body: FCMRegistrationTokenDto, bearerToken: String): Response<Unit>

}