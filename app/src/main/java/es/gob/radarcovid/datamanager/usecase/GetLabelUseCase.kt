package es.gob.radarcovid.datamanager.usecase

import android.content.Context
import android.util.Log
import es.gob.radarcovid.common.di.scope.PerApplication
import es.gob.radarcovid.common.extensions.toJson
import es.gob.radarcovid.datamanager.repository.PreferencesRepository
import javax.inject.Inject
import javax.inject.Named

@PerApplication
class GetLabelUseCase @Inject constructor(
    @Named("applicationContext") private val context: Context,
    private val repository: PreferencesRepository
) {

    private var labels: Map<String, String> = repository.getLabels()

    fun getText(labelId: String?, default: CharSequence): String =
        labels[labelId] ?: default.toString()

}