package es.gob.covidradar.features.onboarding.di

import android.content.Context
import es.gob.covidradar.common.di.scope.PerActivity
import es.gob.covidradar.features.onboarding.presenter.OnboardingPresenterImpl
import es.gob.covidradar.features.onboarding.protocols.OnboardingPresenter
import es.gob.covidradar.features.onboarding.protocols.OnboardingRouter
import es.gob.covidradar.features.onboarding.protocols.OnboardingView
import es.gob.covidradar.features.onboarding.router.OnboardingRouterImpl
import es.gob.covidradar.features.onboarding.view.OnboardingActivity
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