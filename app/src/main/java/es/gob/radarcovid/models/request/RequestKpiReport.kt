package es.gob.radarcovid.models.request

class RequestKpiReport : ArrayList<RequestKpi>()

data class RequestKpi(val kpi: String, val timestamp: String, val value: Int) {

    companion object {
        const val ATTENUATION_DURATIONS_1 = "ATTENUATION_DURATIONS_1"
        const val ATTENUATION_DURATIONS_2 = "ATTENUATION_DURATIONS_2"
        const val ATTENUATION_DURATIONS_3 = "ATTENUATION_DURATIONS_3"
        const val DAYS_SINCE_LAST_EXPOSURE = "DAYS_SINCE_LAST_EXPOSURE"
        const val MATCHED_KEY_COUNT = "MATCHED_KEY_COUNT"
        const val MAXIMUM_RISK_SCORE = "MAXIMUM_RISK_SCORE"
        const val SUMMATION_RISK_SCORE = "SUMMATION_RISK_SCORE"

        const val MATCH_CONFIRMED = "MATCH_CONFIRMED"
        const val BLUETOOTH_ACTIVATED = "BLUETOOTH_ACTIVATED"
    }

}
