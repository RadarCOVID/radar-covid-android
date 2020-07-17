package es.gob.radarcovid.features.locale.presenter

import es.gob.radarcovid.features.locale.protocols.LocaleSelectionPresenter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionView
import javax.inject.Inject

class LocaleSelectionPresenterImpl @Inject constructor(private val view: LocaleSelectionView) :
    LocaleSelectionPresenter {

    override fun viewReady() {

    }

}