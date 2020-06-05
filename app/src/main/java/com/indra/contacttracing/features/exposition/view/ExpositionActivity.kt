package com.indra.contacttracing.features.exposition.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.indra.contacttracing.R
import com.indra.contacttracing.common.base.BaseActivity
import com.indra.contacttracing.common.base.BaseBackNavigationActivity
import com.indra.contacttracing.features.exposition.protocols.ExpositionPresenter
import com.indra.contacttracing.features.exposition.protocols.ExpositionView
import javax.inject.Inject

class ExpositionActivity : BaseBackNavigationActivity(), ExpositionView {

    companion object {

        fun open(context: Context) {
            context.startActivity(Intent(context, ExpositionActivity::class.java))
        }

    }

    @Inject
    lateinit var presenter: ExpositionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exposition)

        presenter.viewReady()
    }

}
