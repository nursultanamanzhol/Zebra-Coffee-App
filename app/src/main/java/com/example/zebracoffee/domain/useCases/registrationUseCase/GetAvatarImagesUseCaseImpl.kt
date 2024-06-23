package com.example.zebracoffee.domain.useCases.registrationUseCase

import com.example.zebracoffee.domain.entity.AvatarImage
import com.example.zebracoffee.domain.repository.RegistrationRepository
import javax.inject.Inject

class GetAvatarImagesUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
) : GetAvatarImagesUseCase {

    override suspend fun getAvatarImages(bearerToken: String): List<AvatarImage> {
        return repository.getAvatarImages(bearerToken)
    }
}