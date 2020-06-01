package com.indra.contacttracing.features.profile.di

import com.indra.contacttracing.features.profile.presenter.ProfilePresenterImpl
import com.indra.contacttracing.features.profile.protocols.ProfilePresenter
import com.indra.contacttracing.features.profile.protocols.ProfileView
import com.indra.contacttracing.features.profile.view.ProfileFragment
import dagger.Module
import dagger.Provides

@Module
class ProfileModule {

    @Provides
    fun providesView(fragment: ProfileFragment): ProfileView = fragment

    @Provides
    fun providesPresenter(presenter: ProfilePresenterImpl): ProfilePresenter = presenter

}