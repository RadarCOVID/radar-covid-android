package com.indra.contacttracing.datamanager.repository

interface PreferencesRepository {

    fun isOnBoardingCompleted(): Boolean

    fun setOnboardingCompleted(onboardingCompleted: Boolean)

}