package es.gob.radarcovid.features.locale.di

import dagger.Module
import dagger.Provides
import es.gob.radarcovid.common.di.scope.PerFragment
import es.gob.radarcovid.features.locale.presenter.LocaleSelectionPresenterImpl
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionPresenter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionRouter
import es.gob.radarcovid.features.locale.protocols.LocaleSelectionView
import es.gob.radarcovid.features.locale.router.LocaleSelectionRouterImpl
import es.gob.radarcovid.features.locale.view.LocaleSelectionFragment

@Module
class LocaleSelectionModule {

    @Provides
    fun providesView(fragment: LocaleSelectionFragment): LocaleSelectionView = fragment

    @Provides
    @PerFragment
    fun providesPresenter(presenter: LocaleSelectionPresenterImpl): LocaleSelectionPresenter =
        presenter

    @Provides
    @PerFragment
    fun providesRouter(router: LocaleSelectionRouterImpl): LocaleSelectionRouter = router

}