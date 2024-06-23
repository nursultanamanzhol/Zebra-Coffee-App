package com.example.zebracoffee.di

import com.example.zebracoffee.data.network.local.LocalRepositoryImpl
import com.example.zebracoffee.data.network.remote.repositoryImpl.CoffeeShopsRepositoryImpl
import com.example.zebracoffee.data.network.remote.repositoryImpl.HomeRepositoryImpl
import com.example.zebracoffee.data.network.remote.repositoryImpl.LoyaltyCardRepositoryImpl
import com.example.zebracoffee.data.network.remote.repositoryImpl.ProfileRepositoryImpl
import com.example.zebracoffee.data.network.remote.repositoryImpl.RegistrationRepositoryImpl
import com.example.zebracoffee.domain.repository.CoffeeShopsRepository
import com.example.zebracoffee.domain.repository.HomeRepository
import com.example.zebracoffee.domain.repository.LocalRepository
import com.example.zebracoffee.domain.repository.LoyaltyCardRepository
import com.example.zebracoffee.domain.repository.ProfileRepository
import com.example.zebracoffee.domain.repository.RegistrationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun bindAuthenticationRepository(impl: RegistrationRepositoryImpl): RegistrationRepository

    @Binds
    fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository

    @Binds
    fun bindLocalRepository(impl: LocalRepositoryImpl): LocalRepository

    @Binds
    fun bindLoyaltyCardRepository(impl: LoyaltyCardRepositoryImpl): LoyaltyCardRepository

    @Binds
    fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun bindCoffeeShopRepository(impl: CoffeeShopsRepositoryImpl): CoffeeShopsRepository
}