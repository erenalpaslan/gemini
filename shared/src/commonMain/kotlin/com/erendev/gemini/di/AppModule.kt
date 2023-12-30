package com.erendev.gemini.di

import com.erendev.gemini.data.database.databaseModule
import com.erendev.gemini.data.network.networkModule
import com.erendev.gemini.data.repository.ChatRepositoryImpl
import com.erendev.gemini.domain.repository.ChatRepository
import com.erendev.gemini.domain.repository.repositoryModule
import com.erendev.gemini.domain.usecase.useCaseModule
import com.erendev.gemini.presentation.features.home.homeModule
import com.erendev.gemini.presentation.features.onboarding.onboardingModule
import com.erendev.gemini.presentation.features.splash.splashModule
import com.erendev.gemini.presentation.features.viewdetail.viewDetailModule
import com.erendev.gemini.presentation.features.welcome.welcomeModule
import com.erendev.gemini.utils.dispatchers.AppCoroutineDispatchers
import com.erendev.gemini.utils.dispatchers.AppCoroutineDispatchersImpl
import org.koin.dsl.module

val appModule = module {
    single<AppCoroutineDispatchers> { AppCoroutineDispatchersImpl() }

    includes(
        databaseModule,
        networkModule,
        repositoryModule,
        useCaseModule,
        splashModule,
        onboardingModule,
        welcomeModule,
        homeModule,
        viewDetailModule
    )
}