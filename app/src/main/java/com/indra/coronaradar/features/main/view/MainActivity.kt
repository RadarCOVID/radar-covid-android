package com.indra.coronaradar.features.main.view

import android.os.Bundle
import com.indra.coronaradar.R
import com.indra.coronaradar.common.base.BaseActivity
import com.indra.coronaradar.common.view.CMDialog
import com.indra.coronaradar.datamanager.usecase.GetExampleUseCase
import com.indra.coronaradar.features.main.protocols.MainPresenter
import com.indra.coronaradar.features.main.protocols.MainView
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
            presenter.onBackPressed()
        else
            bottomNavigation.selectedItemId = R.id.menuItemHome
    }

    override fun showExitConfirmationDialog() {
        CMDialog.createDialog(
            this, "",
            getString(R.string.warning_exit_application_message),
            getString(R.string.warning_exit_application_button), null
        ) {
            presenter.onExitConfirmed()
        }.show()
    }
}
