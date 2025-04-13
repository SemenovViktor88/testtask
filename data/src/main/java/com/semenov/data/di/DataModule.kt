package com.semenov.data.di

import com.semenov.data.AppInfoProvider
import com.semenov.data.signup.api.SignUpApi
import com.semenov.data.signup.SignUpRepositoryImpl
import com.semenov.data.users.UsersRepositoryImpl
import com.semenov.data.users.api.UsersApi
import com.semenov.domain.signuprepository.SignUpRepository
import com.semenov.domain.usersrepository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun createRetrofit(infoProvider: AppInfoProvider): Retrofit {
        return Retrofit.Builder()
            .baseUrl(infoProvider.baseHost)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun usersApi(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersRepository(usersApi: UsersApi): UsersRepository {
        return UsersRepositoryImpl(usersApi)
    }

    @Provides
    @Singleton
    fun signUpApi(retrofit: Retrofit): SignUpApi {
        return retrofit.create(SignUpApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSignUpRepository(signUpApi: SignUpApi): SignUpRepository {
        return SignUpRepositoryImpl(signUpApi)
    }
}