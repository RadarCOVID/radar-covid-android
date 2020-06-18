package es.gob.covidradar.features.helpline.di

import android.content.Context
import es.gob.covidradar.features.helpline.presenter.HelplinePresenterImpl
import es.gob.covidradar.features.helpline.protocols.HelplinePresenter
import es.gob.covidradar.features.helpline.protocols.HelplineRouter
import es.gob.covidradar.features.helpline.protocols.HelplineView
import es.gob.covidradar.features.helpline.router.HelplineRouterImpl
import es.gob.covidradar.features.helpline.view.HelplineFragment
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