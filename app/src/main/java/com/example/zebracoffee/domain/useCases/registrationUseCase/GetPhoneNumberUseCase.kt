package com.example.zebracoffee.domain.useCases.registrationUseCase

import com.example.zebracoffee.data.modelDto.PhoneNumberResponse
import retrofit2.Response

interface GetPhoneNumberUseCase {

    suspend fun getPhoneNumber(phone_number:String): Response<PhoneNumberResponse>

}