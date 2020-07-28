package es.gob.radarcovid.features.locale.presenter

import es.gob.radarcovid.datamanager.usecase.GetLocaleInfoUseCase
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionPresenter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionView
import es.gob.radarcovid.models.domain.LocaleInfo
import javax.inject.Inject

class LocaleSelectionPresenterImpl @Inject constructor(
    private val view: LocaleSelectionView,
    private val getLocaleInfoUseCase: GetLocaleInfoUseCase
) : LocaleSelectionPresenter {

    private val localeInfo: LocaleInfo = getLocaleInfoUseCase.getLocaleInfo()

    override fun viewReady() {
        view.setRegions(localeInfo.regions.map { it.name })
    }

    override fun onApplyButtonClick() {
        getLocaleInfoUseCase.setSelectedRegion(localeInfo.regions[view.getSelectedRegionIndex()].code)
        requestLabels()
    }

    private fun requestLabels() {
        view.showLoading()
        getLocaleInfoUseCase.getLabels(
            onSuccess = {
                view.hideLoading()
            },
            onError = {
                view.hideLoading()
            }
        )
    }

}