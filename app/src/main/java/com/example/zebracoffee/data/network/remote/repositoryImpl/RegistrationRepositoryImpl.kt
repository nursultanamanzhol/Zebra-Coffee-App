package com.example.zebracoffee.data.network.remote.repositoryImpl

import android.util.Log
import com.example.zebracoffee.data.modelDto.FCMRegistrationTokenDto
import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import com.example.zebracoffee.data.modelDto.NotificationListDto
import com.example.zebracoffee.data.modelDto.PhoneNumberBody
import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import com.example.zebracoffee.data.modelDto.UpdateAvatarBodyDto
import com.example.zebracoffee.data.modelDto.UpdateUserDataBody
import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.data.modelDto.VerificationCodeBody
import com.example.zebracoffee.data.modelDto.VerificationCodeResponse
import com.example.zebracoffee.data.network.remote.api.ZebraCoffeeApi
import com.example.zebracoffee.domain.entity.AvatarImage
import com.example.zebracoffee.domain.entity.FCMRegistrationToken
import com.example.zebracoffee.domain.entity.Notification
import com.example.zebracoffee.domain.repository.RegistrationRepository
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: ZebraCoffeeApi,
) : RegistrationRepository {


    override suspend fun getUserData(bearerToken: String): Response<UserDto> {
        val token = "Bearer $bearerToken"
        return api.getUserData(token)
    }

    override suspend fun getPhoneNumber(phoneNumber: String): Response<PhoneNumberResponse> {
        val body = PhoneNumberBody(phoneNumber)
        return api.getPhoneNumber(body)
    }

    override suspend fun verifyCode(
        phoneNumber: String,
        smsCode: Int
    ): Response<VerificationCodeResponse> {
        val body = VerificationCodeBody(phoneNumber, smsCode)
        return api.verifyCode(body)
    }


    override suspend fun getAvatarImages(bearerToken: String): List<AvatarImage> {
        val token = "Bearer $bearerToken"
        return api.getAvatarImage(token).results
    }

    override suspend fun updateUserData(
        name: String,
        phone: String,
        birthDate: String,
        avatarId: Int,
        bearerToken: String,
    ): Response<UpdatedUserInfo> {
        val token = "Bearer $bearerToken"
        val body = UpdateUserDataBody(name, phone, birthDate, avatarId)
        return api.updateUserData(body, token)
    }

    override suspend fun updateAvatarImage(
        avatarChoice: Int,
        bearerToken: String
    ): Response<PhoneNumberResponse> {
        val body = UpdateAvatarBodyDto(avatarChoice)
        val token = "Bearer $bearerToken"
        return api.updateAvatar(body, token)
    }

    override suspend fun getLoyaltyCardInfo(bearerToken: String): Response<LoyaltyCardResponseDto> {
        val token = "Bearer $bearerToken"
        return api.getLoyaltyCardInfo(token)
    }

    override suspend fun getNotificationMessages(bearerToken: String, page: Int, per_page: Int): NotificationListDto {
        val token = "Bearer $bearerToken"
        return api.getNotifications(token, page, per_page)
    }

    override suspend fun setFCMRegistrationToken(
        body: FCMRegistrationTokenDto,
        bearerToken: String
    ): Response<Unit> {
        val token = "Bearer $bearerToken"
        return api.setFCMRegistrationToken(token, body)
    }

//    override suspend fun refreshToken(/*bearerToken: String,*/refresh: String): Call<RefreshTokenResponse> {
//        val token = "Bearer $bearerToken"
//        val body = RefreshTokenBody(refresh)
//        return basicApi.refreshToken(/*token,*/body)
//    }
}