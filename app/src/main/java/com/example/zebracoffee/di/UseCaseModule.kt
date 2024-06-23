package com.example.zebracoffee.di

import com.example.zebracoffee.domain.useCases.coffeeShopsUseCase.CreateNewReviewUseCase
import com.example.zebracoffee.domain.useCases.coffeeShopsUseCase.CreateNewReviewUseCaseImpl
import com.example.zebracoffee.domain.useCases.coffeeShopsUseCase.GetCoffeeShopsUseCase
import com.example.zebracoffee.domain.useCases.coffeeShopsUseCase.GetCoffeeShopsUseCaseImpl
import com.example.zebracoffee.domain.useCases.coffeeShopsUseCase.UpdateReviewUseCase
import com.example.zebracoffee.domain.useCases.coffeeShopsUseCase.UpdateReviewUseCaseImpl
import com.example.zebracoffee.domain.useCases.homeUseCase.CheckAppVersionUseCase
import com.example.zebracoffee.domain.useCases.homeUseCase.CheckAppVersionUseCaseImpl
import com.example.zebracoffee.domain.useCases.homeUseCase.GetCategoryStoriesUseCase
import com.example.zebracoffee.domain.useCases.homeUseCase.GetCategoryStoriesUseCaseImpl
import com.example.zebracoffee.domain.useCases.homeUseCase.GetNewsByIdUseCase
import com.example.zebracoffee.domain.useCases.homeUseCase.GetNewsByIdUseCaseImpl
import com.example.zebracoffee.domain.useCases.homeUseCase.GetNewsListUseCase
import com.example.zebracoffee.domain.useCases.homeUseCase.GetNewsListUseCaseImpl
import com.example.zebracoffee.domain.useCases.homeUseCase.GetUserQrUseCase
import com.example.zebracoffee.domain.useCases.homeUseCase.GetUserQrUseCaseImpl
import com.example.zebracoffee.domain.useCases.homeUseCase.UpdateUserDataUseCase
import com.example.zebracoffee.domain.useCases.homeUseCase.UpdateUserDataUseCaseImpl
import com.example.zebracoffee.domain.useCases.localUseCase.DeleteUserUseCase
import com.example.zebracoffee.domain.useCases.localUseCase.DeleteUserUseCaseImpl
import com.example.zebracoffee.domain.useCases.localUseCase.LocalUserInfoUseCase
import com.example.zebracoffee.domain.useCases.localUseCase.LocalUserInfoUseCaseImpl
import com.example.zebracoffee.domain.useCases.localUseCase.SaveUserDataLocalUseCase
import com.example.zebracoffee.domain.useCases.localUseCase.SaveUserDataLocalUseCaseImpl
import com.example.zebracoffee.domain.useCases.loyaltyCardUseCase.FCMRegistrationTokenUseCase
import com.example.zebracoffee.domain.useCases.loyaltyCardUseCase.FCMRegistrationTokenUseCaseImpl
import com.example.zebracoffee.domain.useCases.loyaltyCardUseCase.GetLoyaltyCardProgressUseCase
import com.example.zebracoffee.domain.useCases.loyaltyCardUseCase.GetLoyaltyCardProgressUseCaseImpl
import com.example.zebracoffee.domain.useCases.loyaltyCardUseCase.GetNotificationUseCase
import com.example.zebracoffee.domain.useCases.loyaltyCardUseCase.GetNotificationUseCaseImpl
import com.example.zebracoffee.domain.useCases.menuUseCases.GetMenuByShopIdUseCase
import com.example.zebracoffee.domain.useCases.menuUseCases.GetMenuByShopIdUseCaseImpl
import com.example.zebracoffee.domain.useCases.menuUseCases.GetSearchHistoryUseCase
import com.example.zebracoffee.domain.useCases.menuUseCases.GetSearchHistoryUseCaseImpl
import com.example.zebracoffee.domain.useCases.menuUseCases.GetSearchProductUseCase
import com.example.zebracoffee.domain.useCases.menuUseCases.GetSearchProductUseCaseImpl
import com.example.zebracoffee.domain.useCases.profileuseCase.DeleteAccountUseCase
import com.example.zebracoffee.domain.useCases.profileuseCase.DeleteAccountUseCaseImpl
import com.example.zebracoffee.domain.useCases.profileuseCase.LogoutUseCase
import com.example.zebracoffee.domain.useCases.profileuseCase.LogoutUseCaseImpl
import com.example.zebracoffee.domain.useCases.profileuseCase.UpdateProfileInfoUseCase
import com.example.zebracoffee.domain.useCases.profileuseCase.UpdateProfileInfoUseCaseImpl
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetAvatarImagesUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetAvatarImagesUseCaseImpl
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetLoyaltyCardInfoUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetLoyaltyCardInfoUseCaseImpl
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetPhoneNumberUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetPhoneNumberUseCaseImpl
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetUserDataUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetUserDataUseCaseImpl
import com.example.zebracoffee.domain.useCases.registrationUseCase.UpdateAvatarImageUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.UpdateAvatarImageUseCaseImpl
import com.example.zebracoffee.domain.useCases.registrationUseCase.VerificationCodeUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.VerificationCodeUseCaseImpl
import com.example.zebracoffee.domain.useCases.reviewUseCase.GetReviewByShopIdUseCase
import com.example.zebracoffee.domain.useCases.reviewUseCase.GetReviewByShopIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface UseCaseModule {

    @Binds
    fun bindGetPhoneNumberUseCase(impl: GetPhoneNumberUseCaseImpl): GetPhoneNumberUseCase

    @Binds
    fun bindVerifyCodeUseCase(impl: VerificationCodeUseCaseImpl): VerificationCodeUseCase

    @Binds
    fun bindUpdateProfileInfo(impl: UpdateProfileInfoUseCaseImpl): UpdateProfileInfoUseCase

    @Binds
    fun bindAvatarImages(impl: GetAvatarImagesUseCaseImpl): GetAvatarImagesUseCase

    @Binds
    fun bindUpdateAvatarImage(impl: UpdateAvatarImageUseCaseImpl): UpdateAvatarImageUseCase

    @Binds
    fun bindGetLoyaltyCarInfo(impl: GetLoyaltyCardInfoUseCaseImpl): GetLoyaltyCardInfoUseCase

    @Binds
    fun bindNotificationMessages(impl: GetNotificationUseCaseImpl): GetNotificationUseCase

    @Binds
    fun bindGetStoriesCategory(impl: GetCategoryStoriesUseCaseImpl): GetCategoryStoriesUseCase

    @Binds
    fun bindGetUserData(impl: GetUserDataUseCaseImpl): GetUserDataUseCase

    @Binds
    fun bindGetLocalUserData(impl: LocalUserInfoUseCaseImpl): LocalUserInfoUseCase

    @Binds
    fun bindGetNewsListUseCase(impl: GetNewsListUseCaseImpl): GetNewsListUseCase

    @Binds
    fun bindGetUserQrUseCase(impl: GetUserQrUseCaseImpl): GetUserQrUseCase

    @Binds
    fun bindLoyaltyCardProgress(impl: GetLoyaltyCardProgressUseCaseImpl): GetLoyaltyCardProgressUseCase

    @Binds
    fun bindFCMRegistrationToken(impl: FCMRegistrationTokenUseCaseImpl): FCMRegistrationTokenUseCase

    @Binds
    fun bindCoffeeShopsUseCase(impl: GetCoffeeShopsUseCaseImpl): GetCoffeeShopsUseCase

    @Binds
    fun bindLogoutUseCase(impl: LogoutUseCaseImpl): LogoutUseCase

    @Binds
    fun bindDeleteAccountUseCase(impl: DeleteAccountUseCaseImpl): DeleteAccountUseCase

    @Binds
    fun bindUpdateUserDataUseCase(impl: UpdateUserDataUseCaseImpl): UpdateUserDataUseCase

    @Binds
    fun bindSaveUserDataLocalUseCase(impl: SaveUserDataLocalUseCaseImpl): SaveUserDataLocalUseCase

    @Binds
    fun bindCheckAppVersionUseCase(impl: CheckAppVersionUseCaseImpl): CheckAppVersionUseCase

    @Binds
    fun bindGetNewsByIdUseCase(impl: GetNewsByIdUseCaseImpl): GetNewsByIdUseCase

    @Binds
    fun bindDeleteUserUseCase(impl: DeleteUserUseCaseImpl): DeleteUserUseCase

    @Binds
    fun bindGetReviewByShopIdUseCase(impl: GetReviewByShopIdUseCaseImpl): GetReviewByShopIdUseCase

    @Binds
    fun bindCreateNewReviewUseCase(impl: CreateNewReviewUseCaseImpl): CreateNewReviewUseCase

    @Binds
    fun bindUpdateReviewUseCase(impl: UpdateReviewUseCaseImpl): UpdateReviewUseCase

    @Binds
    fun bindGetMenuByShopIdUseCase(impl: GetMenuByShopIdUseCaseImpl): GetMenuByShopIdUseCase

    @Binds
    fun bindGetSearchHistoryUseCase(impl: GetSearchHistoryUseCaseImpl): GetSearchHistoryUseCase

    @Binds
    fun bindGetSearchProductUseCase(impl: GetSearchProductUseCaseImpl): GetSearchProductUseCase
}