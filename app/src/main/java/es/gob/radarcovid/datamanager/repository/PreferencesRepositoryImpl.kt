package es.gob.radarcovid.datamanager.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.gob.radarcovid.common.extensions.toJson
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class PreferencesRepositoryImpl @Inject constructor(@Named("applicationContext") context: Context) :
    PreferencesRepository {

    companion object {
        private const val PREFERENCES_NAME = "app_preferences"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_UUID = "uuid"
        private const val KEY_POLL_COMPLETED = "poll_completed"
        private const val KEY_INFECTION_REPORT_DATE = "key_infection_report_date"
        private const val KEY_LABELS = "key_labels"
    }

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun isOnBoardingCompleted(): Boolean =
        preferences.getBoolean(KEY_ONBOARDING_COMPLETED, false)

    override fun setOnboardingCompleted(onboardingCompleted: Boolean) {
        preferences
            .edit()
            .putBoolean(KEY_ONBOARDING_COMPLETED, onboardingCompleted)
            .apply()
    }

    override fun getUuid(): String =
        preferences.getString(KEY_UUID, "") ?: ""


    override fun setUuid(uuid: String) {
        preferences
            .edit()
            .putString(KEY_UUID, uuid)
            .apply()
    }

    override fun isPollCompleted(): Boolean =
        preferences.getBoolean(KEY_POLL_COMPLETED, false)

    override fun setPollCompleted(pollCompleted: Boolean) {
        preferences
            .edit()
            .putBoolean(KEY_POLL_COMPLETED, pollCompleted)
            .apply()
    }

    override fun getInfectionReportDate(): Date? {
        val infectionReportDateMillis = preferences.getLong(KEY_INFECTION_REPORT_DATE, 0)
        return if (infectionReportDateMillis == 0L)
            null
        else
            Date(infectionReportDateMillis)
    }

    override fun setInfectionReportDate(date: Date) {
        preferences.edit()
            .putLong(KEY_INFECTION_REPORT_DATE, date.time)
            .apply()
    }

    override fun setLabels(labels: Map<String, String>) {
        preferences.edit()
            .putString(KEY_LABELS, labels.toJson())
            .apply()
    }

    override fun getLabels(): Map<String, String> {
        val itemType = object : TypeToken<HashMap<String, String>>() {}.type
        return Gson().fromJson(preferences.getString(KEY_LABELS, "{}"), itemType)
    }

}