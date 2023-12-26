package com.erendev.gemini.presentation.features.onboarding

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

/**
 * Created by erenalpaslan on 30.09.2023
 */
val onboardingModule = module {
    factoryOf(::OnboardingViewModel)
}