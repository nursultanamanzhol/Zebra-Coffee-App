package com.example.zebracoffee.domain.useCases.registrationUseCase

import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import com.example.zebracoffee.domain.repository.RegistrationRepository
import retrofit2.Response
import javax.inject.Inject

class GetPhoneNumberUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
) : GetPhoneNumberUseCase {
    override suspend fun getPhoneNumber(phone_number:String): Response<PhoneNumberResponse> {
        return repository.getPhoneNumber(phone_number)
    }
}