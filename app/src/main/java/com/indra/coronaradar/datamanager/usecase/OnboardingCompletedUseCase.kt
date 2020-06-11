package com.indra.coronaradar.datamanager.usecase

import com.indra.coronaradar.datamanager.repository.PreferencesRepository
import javax.inject.Inject

class OnboardingCompletedUseCase @Inject constructor(private val repository: PreferencesRepository) {

    fun isOnBoardingCompleted(): Boolean = repository.isOnBoardingCompleted()

    fun setOnboardingCompleted(onboardingCompleted: Boolean) =
        repository.setOnboardingCompleted(onboardingCompleted)

}