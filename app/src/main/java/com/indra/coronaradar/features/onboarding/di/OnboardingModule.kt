package com.indra.coronaradar.features.onboarding.di

import android.content.Context
import com.indra.coronaradar.common.di.scope.PerActivity
import com.indra.coronaradar.features.onboarding.presenter.OnboardingPresenterImpl
import com.indra.coronaradar.features.onboarding.protocols.OnboardingPresenter
import com.indra.coronaradar.features.onboarding.protocols.OnboardingRouter
import com.indra.coronaradar.features.onboarding.protocols.OnboardingView
import com.indra.coronaradar.features.onboarding.router.OnboardingRouterImpl
import com.indra.coronaradar.features.onboarding.view.OnboardingActivity
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