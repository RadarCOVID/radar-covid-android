package es.gob.covidradar.features.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import es.gob.covidradar.R
import es.gob.covidradar.common.base.BaseActivity
import es.gob.covidradar.common.view.CMDialog
import es.gob.covidradar.datamanager.usecase.GetExampleUseCase
import es.gob.covidradar.features.main.protocols.MainPresenter
import es.gob.covidradar.features.main.protocols.MainView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    companion object {

        const val EXTRA_ACTIVATE_RADAR = "extra_activate_radar"

        fun open(context: Context, activateRadar: Boolean) =
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_ACTIVATE_RADAR, activateRadar)
            })

    }

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var useCase: GetExampleUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        presenter.viewReady(intent.getBooleanExtra(EXTRA_ACTIVATE_RADAR, false))
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
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
