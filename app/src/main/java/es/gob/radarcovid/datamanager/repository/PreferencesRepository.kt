package es.gob.radarcovid.datamanager.repository

interface PreferencesRepository {

    fun isOnBoardingCompleted(): Boolean

    fun setOnboardingCompleted(onboardingCompleted: Boolean)

    fun getUuid(): String

    fun setUuid(uuid: String)

    fun isPollCompleted(): Boolean

    fun setPollCompleted(pollCompleted: Boolean)

    fun isInfectionReported(): Boolean

    fun setInfectionReported(reported: Boolean)

}