package com.indra.contacttracing.datamanager.repository

import android.content.Context
import javax.inject.Inject
import javax.inject.Named

private const val PREFERENCES_NAME = "app_preferences"
private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

class PreferencesRepositoryImpl @Inject constructor(@Named("applicationContext") context: Context) :
    PreferencesRepository {

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun isOnBoardingCompleted(): Boolean =
        preferences.getBoolean(KEY_ONBOARDING_COMPLETED, false)

    override fun setOnboardingCompleted(onboardingCompleted: Boolean) {
        preferences
            .edit()
            .putBoolean(KEY_ONBOARDING_COMPLETED, onboardingCompleted)
            .apply()
    }

}