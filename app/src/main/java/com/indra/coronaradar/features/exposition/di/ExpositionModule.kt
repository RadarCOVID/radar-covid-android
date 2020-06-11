package com.indra.coronaradar.features.exposition.di

import android.content.Context
import com.indra.coronaradar.common.di.scope.PerActivity
import com.indra.coronaradar.features.exposition.presenter.ExpositionPresenterImpl
import com.indra.coronaradar.features.exposition.protocols.ExpositionPresenter
import com.indra.coronaradar.features.exposition.protocols.ExpositionRouter
import com.indra.coronaradar.features.exposition.protocols.ExpositionView
import com.indra.coronaradar.features.exposition.router.ExpositionRouterImpl
import com.indra.coronaradar.features.exposition.view.ExpositionActivity
import dagger.Module
import dagger.Provides

@Module
class ExpositionModule {

    @Provides
    fun providesContext(activity: ExpositionActivity): Context = activity

    @Provides
    fun providesView(activity: ExpositionActivity): ExpositionView = activity

    @Provides
    @PerActivity
    fun providesPresenter(presenter: ExpositionPresenterImpl): ExpositionPresenter = presenter

    @Provides
    @PerActivity
    fun providesRouter(router: ExpositionRouterImpl): ExpositionRouter = router

}