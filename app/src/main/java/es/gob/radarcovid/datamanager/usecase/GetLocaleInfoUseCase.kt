package es.gob.radarcovid.datamanager.usecase

import es.gob.radarcovid.common.base.asyncRequest
import es.gob.radarcovid.common.base.mapperScope
import es.gob.radarcovid.datamanager.mapper.LanguagesDataMapper
import es.gob.radarcovid.datamanager.mapper.RegionsDataMapper
import es.gob.radarcovid.datamanager.repository.ContentfulRepository
import es.gob.radarcovid.models.domain.Language
import es.gob.radarcovid.models.domain.LocaleInfo
import es.gob.radarcovid.models.domain.Region
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetLocaleInfoUseCase @Inject constructor(
    private val contentfulRepository: ContentfulRepository,
    private val languagesDataMapper: LanguagesDataMapper,
    private val regionsDataMapper: RegionsDataMapper
) {

    fun observeLocaleInfo(): Observable<LocaleInfo> =
        Observable.zip<List<Language>, List<Region>, LocaleInfo>(
            observeLanguages(),
            observeRegions(),
            BiFunction { languages, regions ->
                LocaleInfo(languages, regions)
            }
        ).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

    private fun observeLanguages(): Observable<List<Language>> = Observable.create { emitter ->
        getLanguages(
            onSuccess = {
                emitter.onNext(it)
                emitter.onComplete()
            },
            onError = {
                emitter.onError(it)
                emitter.onComplete()
            }
        )
    }

    private fun observeRegions(): Observable<List<Region>> = Observable.create { emitter ->
        getRegions(
            onSuccess = {
                emitter.onNext(it)
                emitter.onComplete()
            },
            onError = {
                emitter.onError(it)
                emitter.onComplete()
            }
        )
    }

    fun getLanguages(onSuccess: (List<Language>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            mapperScope(contentfulRepository.getLanguages()) {
                languagesDataMapper.transform(it)
            }
        }
    }

    fun getRegions(onSuccess: (List<Region>) -> Unit, onError: (Throwable) -> Unit) {
        asyncRequest(onSuccess, onError) {
            mapperScope(contentfulRepository.getRegions()) {
                regionsDataMapper.transform(it)
            }
        }
    }

}