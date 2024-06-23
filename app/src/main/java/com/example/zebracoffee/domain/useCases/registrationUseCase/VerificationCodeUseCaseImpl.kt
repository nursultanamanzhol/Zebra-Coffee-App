package com.example.zebracoffee.domain.useCases.registrationUseCase

import com.example.zebracoffee.data.modelDto.VerificationCodeResponse
import com.example.zebracoffee.domain.repository.RegistrationRepository
import retrofit2.Response
import javax.inject.Inject

class VerificationCodeUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
) : VerificationCodeUseCase {
    override suspend fun verifyCode(
        phone_number: String,
        sms_code: Int
    ): Response<VerificationCodeResponse> {
        return repository.verifyCode(phone_number, sms_code)
    }
}