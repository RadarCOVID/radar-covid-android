package com.indra.coronaradar.features.exposure.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.indra.coronaradar.common.di.scope.PerActivity
import com.indra.coronaradar.datamanager.repository.ContactTracingRepository
import com.indra.coronaradar.datamanager.repository.ContactTracingRepositoryImpl
import com.indra.coronaradar.features.exposure.presenter.ExposurePresenterImpl
import com.indra.coronaradar.features.exposure.protocols.ExposurePresenter
import com.indra.coronaradar.features.exposure.protocols.ExposureRouter
import com.indra.coronaradar.features.exposure.protocols.ExposureView
import com.indra.coronaradar.features.exposure.router.ExposureRouterImpl
import com.indra.coronaradar.features.exposure.view.ExposureActivity
import dagger.Module
import dagger.Provides

@Module
class ExposureModule {

    @Provides
    fun providesContext(activity: ExposureActivity): Context = activity

    @Provides
    fun providesActivity(activity: ExposureActivity): AppCompatActivity = activity

    @Provides
    fun providesView(activity: ExposureActivity): ExposureView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: ExposurePresenterImpl): ExposurePresenter = presenter

    @Provides
    @PerActivity
    fun providesRouter(router: ExposureRouterImpl): ExposureRouter = router

    @Provides
    @PerActivity
    fun providesContactTracingRepository(repository: ContactTracingRepositoryImpl): ContactTracingRepository =
        repository

}