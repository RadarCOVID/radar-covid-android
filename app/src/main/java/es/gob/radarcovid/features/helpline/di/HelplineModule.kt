package es.gob.radarcovid.features.helpline.di

import android.content.Context
import es.gob.radarcovid.features.helpline.presenter.HelplinePresenterImpl
import es.gob.radarcovid.features.helpline.protocols.HelplinePresenter
import es.gob.radarcovid.features.helpline.protocols.HelplineRouter
import es.gob.radarcovid.features.helpline.protocols.HelplineView
import es.gob.radarcovid.features.helpline.router.HelplineRouterImpl
import es.gob.radarcovid.features.helpline.view.HelplineFragment
import dagger.Module
import dagger.Provides

@Module
class HelplineModule {

    @Provides
    fun providesContext(fragment: HelplineFragment): Context = fragment.context!!

    @Provides
    fun providesView(fragment: HelplineFragment): HelplineView = fragment

    @Provides
    fun providesPresenter(presenter: HelplinePresenterImpl): HelplinePresenter = presenter

    @Provides
    fun providesRouter(router: HelplineRouterImpl): HelplineRouter = router

}