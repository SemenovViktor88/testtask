package com.semenov.testtask.di

import android.app.Application
import com.semenov.data.AppInfoProvider
import com.semenov.testtask.util.AppInfoProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideInfoProvider(
        context: Application,
    ): AppInfoProvider {
        return AppInfoProviderImpl(context)
    }
}