package com.indra.coronaradar.features.mydata.di

import com.indra.coronaradar.features.mydata.presenter.MyDataPresenterImpl
import com.indra.coronaradar.features.mydata.protocols.MyDataPresenter
import com.indra.coronaradar.features.mydata.protocols.MyDataView
import com.indra.coronaradar.features.mydata.view.MyDataFragment
import dagger.Module
import dagger.Provides

@Module
class MyDataModule {

    @Provides
    fun providesView(fragment: MyDataFragment): MyDataView = fragment

    @Provides
    fun providesPresenter(presenter: MyDataPresenterImpl): MyDataPresenter = presenter

}