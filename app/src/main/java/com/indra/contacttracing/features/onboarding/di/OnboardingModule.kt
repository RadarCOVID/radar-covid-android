package com.indra.contacttracing.features.onboarding.di

import android.content.Context
import com.indra.contacttracing.common.di.scope.PerActivity
import com.indra.contacttracing.features.onboarding.presenter.OnboardingPresenterImpl
import com.indra.contacttracing.features.onboarding.protocols.OnboardingPresenter
import com.indra.contacttracing.features.onboarding.protocols.OnboardingRouter
import com.indra.contacttracing.features.onboarding.protocols.OnboardingView
import com.indra.contacttracing.features.onboarding.router.OnboardingRouterImpl
import com.indra.contacttracing.features.onboarding.view.OnboardingActivity
import dagger.Module
import dagger.Provides

@Module
class OnboardingModule {

    @Provides
    fun providesContext(activity: OnboardingActivity): Context = activity

    @Provides
    fun providesView(activity: OnboardingActivity): OnboardingView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: OnboardingPresenterImpl): OnboardingPresenter = presenter

    @Provides
    @PerActivity
    fun providesRouter(router: OnboardingRouterImpl): OnboardingRouter = router

}