package com.indra.coronaradar.datamanager.repository

interface PreferencesRepository {

    fun isOnBoardingCompleted(): Boolean

    fun setOnboardingCompleted(onboardingCompleted: Boolean)

}