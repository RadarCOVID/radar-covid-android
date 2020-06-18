package es.gob.covidradar.datamanager.repository

interface PreferencesRepository {

    fun isOnBoardingCompleted(): Boolean

    fun setOnboardingCompleted(onboardingCompleted: Boolean)

    fun getUuid(): String

    fun setUuid(uuid: String)

}