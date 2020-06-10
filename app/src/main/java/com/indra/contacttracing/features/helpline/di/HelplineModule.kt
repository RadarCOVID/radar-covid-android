package com.indra.contacttracing.features.helpline.di

import android.content.Context
import com.indra.contacttracing.features.helpline.presenter.HelplinePresenterImpl
import com.indra.contacttracing.features.helpline.protocols.HelplinePresenter
import com.indra.contacttracing.features.helpline.protocols.HelplineRouter
import com.indra.contacttracing.features.helpline.protocols.HelplineView
import com.indra.contacttracing.features.helpline.router.HelplineRouterImpl
import com.indra.contacttracing.features.helpline.view.HelplineFragment
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