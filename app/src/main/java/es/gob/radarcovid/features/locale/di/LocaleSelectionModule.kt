package es.gob.radarcovid.features.locale.di

import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerSubFragment
import es.gob.radarcovid.features.locale.presenter.LocaleSelectionPresenterImpl
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionPresenter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionView
import es.gob.radarcovid.features.locale.view.LocaleSelectionFragment

@Module
class LocaleSelectionModule {

    @Provides
    fun providesView(fragment: LocaleSelectionFragment): LocaleSelectionView = fragment

    @Provides
    @PerSubFragment
    fun providesPresenter(presenter: LocaleSelectionPresenterImpl): LocaleSelectionPresenter =
        presenter

}