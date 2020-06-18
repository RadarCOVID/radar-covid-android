package es.gob.covidradar.features.mydata.di

import es.gob.covidradar.features.mydata.presenter.MyDataPresenterImpl
import es.gob.covidradar.features.mydata.protocols.MyDataPresenter
import es.gob.covidradar.features.mydata.protocols.MyDataView
import es.gob.covidradar.features.mydata.view.MyDataFragment
import dagger.Module
import dagger.Provides

@Module
class MyDataModule {

    @Provides
    fun providesView(fragment: MyDataFragment): MyDataView = fragment

    @Provides
    fun providesPresenter(presenter: MyDataPresenterImpl): MyDataPresenter = presenter

}