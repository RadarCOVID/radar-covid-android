package com.indra.coronaradar.features.exposure.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.indra.coronaradar.common.di.scope.PerActivity
import com.indra.coronaradar.datamanager.repository.ContactTracingRepository
import com.indra.coronaradar.datamanager.repository.ContactTracingRepositoryImpl
import com.indra.coronaradar.features.exposure.presenter.ExpositionPresenterImpl
import com.indra.coronaradar.features.exposure.protocols.ExpositionPresenter
import com.indra.coronaradar.features.exposure.protocols.ExpositionRouter
import com.indra.coronaradar.features.exposure.protocols.ExpositionView
import com.indra.coronaradar.features.exposure.router.ExpositionRouterImpl
import com.indra.coronaradar.features.exposure.view.ExpositionActivity
import dagger.Module
import dagger.Provides

@Module
class ExpositionModule {

    @Provides
    fun providesContext(activity: ExpositionActivity): Context = activity

    @Provides
    fun providesActivity(activity: ExpositionActivity): AppCompatActivity = activity

    @Provides
    fun providesView(activity: ExpositionActivity): ExpositionView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: ExpositionPresenterImpl): ExpositionPresenter = presenter

    @Provides
    @PerActivity
    fun providesRouter(router: ExpositionRouterImpl): ExpositionRouter = router

    @Provides
    @PerActivity
    fun providesContactTracingRepository(repository: ContactTracingRepositoryImpl): ContactTracingRepository =
        repository

}