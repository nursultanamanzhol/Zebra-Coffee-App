package com.example.zebracoffee.domain.useCases.registrationUseCase

import com.example.zebracoffee.data.modelDto.VerificationCodeResponse
import retrofit2.Response

interface VerificationCodeUseCase {

    suspend fun verifyCode(phone_number: String,sms_code: Int): Response<VerificationCodeResponse>

}