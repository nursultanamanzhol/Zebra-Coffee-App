package com.example.zebracoffee.domain.useCases.registrationUseCase

import com.example.zebracoffee.domain.entity.AvatarImage

interface GetAvatarImagesUseCase {

    suspend fun getAvatarImages(bearerToken: String): List<AvatarImage>

}