package com.indra.contacttracing.features.helpline.di

import com.indra.contacttracing.features.helpline.presenter.HelpLinePresenterImpl
import com.indra.contacttracing.features.helpline.protocols.HelplinePresenter
import com.indra.contacttracing.features.helpline.protocols.HelplineView
import com.indra.contacttracing.features.helpline.view.HelplineFragment
import dagger.Module
import dagger.Provides

@Module
class HelplineModule {

    @Provides
    fun providesView(fragment: HelplineFragment): HelplineView = fragment

    @Provides
    fun providesPresenter(presenter: HelpLinePresenterImpl): HelplinePresenter = presenter

}