package com.kotlin.finalclenarchkotlin.di

import com.kotlin.finalclenarchkotlin.data.remote.apiservice.ApiService
import com.kotlin.finalclenarchkotlin.data.repository.LoginRepoImpl
import com.kotlin.finalclenarchkotlin.domain.repository.LoginRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(apiServices: ApiService): LoginRepo {
        return LoginRepoImpl(
            loginApiService = apiServices
        )
    }
}