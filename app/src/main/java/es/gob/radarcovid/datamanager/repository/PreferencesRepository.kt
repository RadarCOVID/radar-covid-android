package es.gob.radarcovid.datamanager.repository

import es.gob.radarcovid.models.domain.Language
import es.gob.radarcovid.models.domain.Region
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

    fun setSelectedRegion(region: String)

    fun getSelectedRegion(): String

    fun setSelectedLanguage(language: String)

    fun getSelectedLanguage(): String

    fun setLabels(labels: Map<String, String>)

    fun getLabels(): Map<String, String>

    fun setLanguages(languages: List<Language>)

    fun getLanguages(): List<Language>

    fun setRegions(regions: List<Region>)

    fun getRegions(): List<Region>


}