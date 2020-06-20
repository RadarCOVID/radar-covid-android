package es.gob.covidradar.datamanager.usecase

import es.gob.covidradar.common.base.asyncRequest
import es.gob.covidradar.common.base.mapperScope
import es.gob.covidradar.datamanager.mapper.SettingsDataMapper
import es.gob.covidradar.datamanager.repository.ApiRepository
import es.gob.covidradar.models.domain.Settings
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val settingsDataMapper: SettingsDataMapper
) {

    fun create(onSuccess: (Settings) -> Unit, onError: (Throwable) -> Unit) {
        Observable.create<Settings> {
            getSettings(
                onSuccess = {

                },
                onError = {

                })
        }
    }

    fun getSettings(onSuccess: (Settings) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            val res = apiRepository.getSettings()
            mapperScope {
                settingsDataMapper.transform(res.right().get())
            }
        }

    }

}