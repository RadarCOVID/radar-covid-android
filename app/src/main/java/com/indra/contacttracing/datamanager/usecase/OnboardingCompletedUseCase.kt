package com.indra.contacttracing.datamanager.usecase

import com.indra.contacttracing.datamanager.repository.PreferencesRepository
import javax.inject.Inject

class OnboardingCompletedUseCase @Inject constructor(private val repository: PreferencesRepository) {

    fun isOnBoardingCompleted(): Boolean = repository.isOnBoardingCompleted()

    fun setOnboardingCompleted(onboardingCompleted: Boolean) =
        repository.setOnboardingCompleted(onboardingCompleted)

}