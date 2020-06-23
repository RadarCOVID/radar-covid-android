package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import javax.inject.Inject

class OnboardingCompletedUseCase @Inject constructor(private val repository: PreferencesRepository) {

    fun isOnBoardingCompleted(): Boolean = repository.isOnBoardingCompleted()

    fun setOnboardingCompleted(onboardingCompleted: Boolean) =
        repository.setOnboardingCompleted(onboardingCompleted)

}