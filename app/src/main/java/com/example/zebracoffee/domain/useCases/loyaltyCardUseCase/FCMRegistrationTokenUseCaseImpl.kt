package com.example.zebracoffee.domain.useCases.loyaltyCardUseCase

import android.util.Log
import com.example.zebracoffee.data.modelDto.FCMRegistrationToken
import com.example.zebracoffee.data.modelDto.FCMRegistrationTokenDto
import com.example.zebracoffee.domain.repository.RegistrationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class FCMRegistrationTokenUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
): FCMRegistrationTokenUseCase {
    override suspend fun setFCMRegistrationToken(body: FCMRegistrationTokenDto, bearerToken: String)
            = withContext(Dispatchers.IO) {
        Log.d("MyTag", "body : $body")
        repository.setFCMRegistrationToken(
            FCMRegistrationTokenDto(
                device_token = body.device_token,
                is_client_push_allowed = body.is_client_push_allowed,
                platform = body.platform
            ),
            bearerToken
        )
    }
}