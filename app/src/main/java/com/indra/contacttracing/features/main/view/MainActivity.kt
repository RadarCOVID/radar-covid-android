package com.indra.contacttracing.features.main.view

import android.os.Bundle
import com.indra.contacttracing.R
import com.indra.contacttracing.common.base.BaseActivity
import com.indra.contacttracing.datamanager.usecase.GetExampleUseCase
import com.indra.contacttracing.features.main.protocols.MainPresenter
import com.indra.contacttracing.features.main.protocols.MainView
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var useCase: GetExampleUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.viewReady()
    }

}
