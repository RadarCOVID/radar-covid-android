package es.gob.radarcovid.features.onboarding.di

import android.content.Context
import es.gob.radarcovid.common.di.scope.PerActivity
import es.gob.radarcovid.features.onboarding.presenter.OnboardingPresenterImpl
import es.gob.radarcovid.features.onboarding.protocols.OnboardingPresenter
import es.gob.radarcovid.features.onboarding.protocols.OnboardingRouter
import es.gob.radarcovid.features.onboarding.protocols.OnboardingView
import es.gob.radarcovid.features.onboarding.router.OnboardingRouterImpl
import es.gob.radarcovid.features.onboarding.view.OnboardingActivity
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