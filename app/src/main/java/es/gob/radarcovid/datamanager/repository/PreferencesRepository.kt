package es.gob.radarcovid.datamanager.repository

import java.util.*

interface PreferencesRepository {

    fun isOnBoardingCompleted(): Boolean

    fun setOnboardingCompleted(onboardingCompleted: Boolean)

    fun getUuid(): String

    fun setUuid(uuid: String)

    fun isPollCompleted(): Boolean

    fun setPollCompleted(pollCompleted: Boolean)

    fun getInfectionReportDate(): Date?

    fun setInfectionReportDate(date: Date)

}