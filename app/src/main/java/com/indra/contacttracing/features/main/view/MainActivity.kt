package com.indra.contacttracing.features.main.view

import android.os.Bundle
import com.indra.contacttracing.R
import com.indra.contacttracing.common.base.BaseActivity
import com.indra.contacttracing.datamanager.usecase.GetExampleUseCase
import com.indra.contacttracing.features.main.protocols.MainPresenter
import com.indra.contacttracing.features.main.protocols.MainView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var useCase: GetExampleUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        presenter.viewReady()
    }

    private fun initViews() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuItemHome -> presenter.onHomeButtonClick()
//                R.id.menuItemHealth -> presenter.onHealthButtonClick()
                R.id.menuItemProfile -> presenter.onProfileButtonClick()
                R.id.menuItemHelpline -> presenter.onHelplineButtonClick()
            }
            true
        }

    }

    override fun onBackPressed() {
        if (bottomNavigation.selectedItemId == R.id.menuItemHome)
            super.onBackPressed()
        else
            bottomNavigation.selectedItemId = R.id.menuItemHome
    }
}
